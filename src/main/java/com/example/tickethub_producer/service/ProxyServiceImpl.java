package com.example.tickethub_producer.service;

import com.example.tickethub_producer.repository.RedisRepository;
import com.example.tickethub_producer.repository.UserRepository;
import com.example.tickethub_producer.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService {

    private final TicketSystem ticketSystem;
    private final RedisRepository redisRepository;
    private final JwtProvider validate;

    @Override
    public long createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken) {
        ticketSystem.createTicket(userId, performanceId, time, seatNumber, payment, jwtToken);
        return 0;
    }

    @Override
    public boolean checkTicket(long ticketId, String jwtToken) {
        ticketSystem.checkTicket(ticketId, jwtToken);
        return false;
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
        ticketSystem.checkToken(ticketId, token, jwtToken);
        return false;
    }

    public boolean checkAuthority(String accessToken){
//        String email = validate.getEmail(accessToken);
//        Long Id = validate.getId(accessToken);
//        userRepository.

        return false;
    }
}
