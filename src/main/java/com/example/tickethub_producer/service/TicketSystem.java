package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.ProduceTicketResponse;
import com.example.tickethub_producer.entity.PerformanceTicket;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.repository.PerformanceTicketRepository;
import com.example.tickethub_producer.repository.UserRepository;
import com.example.tickethub_producer.utils.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketSystem implements ProxyService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PerformanceTicketRepository ticketRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.createTicketMessageTopic}")
    private String createTicketMessageTopic;

    @Override
    @Transactional(transactionManager = "kafkaTransactionManager")
    public ProduceTicketResponse createTicket(long performanceId, LocalDateTime time, int[] seatNumber, String payment, String token) {
        String userIdString = Long.toString(jwtProvider.getId(token));
        String performanceIdString = Long.toString(performanceId);
        String timeString = time.toString();

        for(int i=0; i<seatNumber.length; i++){
            String seatNumberString = Integer.toString(seatNumber[i]);

            try {
                kafkaTemplate.executeInTransaction(operations -> {
                    ProducerRecord<String, String> producerRecord = transformMessageStringToJson(
                            userIdString, performanceIdString, timeString, seatNumberString, payment);
                    operations.send(producerRecord);
                    return null; // 트랜잭션 성공 시 반환값 없음
                });

            } catch (Exception e) {
                throw new RuntimeException("Kafka 트랜잭션 중 오류가 발생했습니다.", e);
            }
        }
        return new ProduceTicketResponse("티켓 발급 요청이 정상적으로 처리되었습니다.");
    }

    @Override
    public List<PerformanceTicket> checkUserTicket(String token) {
        User user = userRepository.findById(jwtProvider.getId(token)).get();
        return ticketRepository.findAllByUser(user);
    }

    @Override
    public String checkTicket(long ticketId, String token) {
        PerformanceTicket ticket = ticketRepository.findById(ticketId).get();
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
