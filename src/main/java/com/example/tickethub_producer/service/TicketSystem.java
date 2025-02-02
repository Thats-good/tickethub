package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.ProduceTicketResponse;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.repository.TicketRepository;
import com.example.tickethub_producer.repository.UserRepository;
import com.example.tickethub_producer.utils.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Service
@RequiredArgsConstructor
public class TicketSystem implements ProxyService {

    //private final TicketService ticketService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TicketRepository ticketRepository;

    private final KafkaProducer<String, String> kafkaProducer;

    @Value("${kafka.createTicketMessageTopic}")
    private String createTicketMessageTopic;

    @PostConstruct
    public void init() {
        kafkaProducer.initTransactions();  // 서비스 초기화 시에 한 번만 호출
    }

    @Override
    @Transactional
    public ProduceTicketResponse createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String token) {
        String userIdString = Long.toString(userId);
        String performanceIdString = Long.toString(performanceId);
        String timeString = time.toString();
        String seatNumberString = Integer.toString(seatNumber);

        try{
            kafkaProducer.beginTransaction();
            ProducerRecord<String, String> producerRecord = transformMessageStringToJson(userIdString, performanceIdString, timeString, seatNumberString, payment);
            Future<RecordMetadata> sendFuture = kafkaProducer.send(producerRecord);

            // 메시지 전송 완료 까지 대기 (Timeout 1sec)
            RecordMetadata metadata = sendFuture.get(1, TimeUnit.SECONDS);
            // 트랜잭션 커밋
            kafkaProducer.commitTransaction();
            System.out.println("Message sent to topic " + metadata.topic() + " with offset " + metadata.offset());
            return new ProduceTicketResponse("티켓 발급 요청이 정상적으로 처리되었습니다.");
        }catch (TimeoutException e) {
            kafkaProducer.abortTransaction();
            System.out.println("Timeout while waiting for message send to complete");
            throw new RuntimeException("TimeoutException occurred during message production", e);
        } catch (ExecutionException e) {
            kafkaProducer.abortTransaction();
            System.out.println("Execution exception while sending message");
            throw new RuntimeException("ExecutionException occurred during message production", e);
        } catch (InterruptedException e) {
            kafkaProducer.abortTransaction();
            System.out.println("Interrupted while waiting for message send to complete");
            Thread.currentThread().interrupt();  // Restore interrupted status
            throw new RuntimeException("InterruptedException occurred during message production", e);
        }
    }

    @Override
    public List<Ticket> checkUserTicket(long userId, String token) {
        User user = userRepository.findById(userId).get();
        return ticketRepository.findAllByUser(user);
    }

    @Override
    public String checkTicket(long ticketId, String token) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        String ticketToken = jwtProvider.createTicketToken(ticket);
        ticket.setToken(ticketToken);
        ticketRepository.save(ticket);
        return ticketToken;
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        return ticket.getToken().equals(token) && jwtProvider.isExpiredToken(token);
    }

    private ProducerRecord<String, String> transformMessageStringToJson(String userId, String performanceId, String time, String seatNumber, String payment) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 데이터를 담을 Map 생성
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("userId", userId);
        messageMap.put("performanceId", performanceId);
        messageMap.put("time", time);
        messageMap.put("seatNumber", seatNumber);
        messageMap.put("payment", payment);

        try {
            // Map을 JSON 문자열로 변환
            String messageValue = objectMapper.writeValueAsString(messageMap);
            return new ProducerRecord<>(createTicketMessageTopic, userId, messageValue);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to serialize message to JSON", e);
        }
    }
}
