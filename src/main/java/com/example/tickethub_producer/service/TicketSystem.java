package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.ProduceTicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TicketSystem implements ProxyService {

//    private final TicketService ticketService;

    @Override
    public long createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken) {
//        ProduceTicketResponse ticket = ticketService.createTicket(userId, performanceId, time, seatNumber, payment);
        return 0;
    }

    @Override
    public boolean checkTicket(long ticketId, String jwtToken) {
//        ticketService.checkTicket(ticketId);
        return false;
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
//        ticketService.checkToken(ticketId, token);
        return false;
    }
}
