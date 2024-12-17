package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.ProduceTicketResponse;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.repository.RedisRepository;
import com.example.tickethub_producer.repository.UserRepository;
import com.example.tickethub_producer.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Primary
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService {

    private final TicketSystem ticketSystem;
    private final RedisRepository redisRepository;
    private final JwtProvider validate;

    @Override
    public ProduceTicketResponse createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken) {
        if(checkAuthority(jwtToken)){
            return ticketSystem.createTicket(userId, performanceId, time, seatNumber, payment, jwtToken);
        }else{
            throw new RuntimeException("로그인 해주세요");
        }
    }

    @Override
    public List<Ticket> checkUserTicket(long userId, String jwtToken) {
        if(checkAuthority(jwtToken)){
            return ticketSystem.checkUserTicket(userId, jwtToken);
        }else{
            throw new RuntimeException("로그인 해주세요");
        }
    }

    @Override
    public String checkTicket(long ticketId, String jwtToken) {
        if(checkAuthority(jwtToken)){
            return ticketSystem.checkTicket(ticketId, jwtToken);
        }else{
            throw new RuntimeException("로그인 해주세요");
        }
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
        if(checkAuthority(jwtToken)){
            return ticketSystem.checkToken(ticketId, token, jwtToken);
        }else{
            throw new RuntimeException("로그인 해주세요");
        }
    }

    public boolean checkAuthority(String accessToken){
        String email = validate.getEmail(accessToken);
        return (Objects.equals(accessToken, redisRepository.findById(email).get().getAccessToken())) && validate.isExpiredToken(accessToken);
    }
}
