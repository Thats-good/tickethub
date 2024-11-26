package com.example.tickethub_producer.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProxyServiceImpl implements ProxyService {

    private final TicketSystem ticketSystem;

    public ProxyServiceImpl(TicketSystem ticketSystem) {
        this.ticketSystem = ticketSystem;
    }

    @Override
    public long createTicket(int performanceId, LocalDateTime time, int seatNumber, String paymentStrategy, String jwtToken) {
        return 0;
    }

    @Override
    public long createUserTicket(String userId, String jwtToken) {
        return 0;
    }

    @Override
    public boolean checkTicket(long ticketId, String jwtToken) {
        return false;
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
        return false;
    }
}
