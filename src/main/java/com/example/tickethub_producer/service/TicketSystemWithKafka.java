package com.example.tickethub_producer.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketSystemWithKafka implements TicketSystem {

    private final SeatService seatService;

    private final PaymentService paymentService;

    private final TicketService ticketService;

    public TicketSystemWithKafka(SeatService seatService, PaymentService paymentService, TicketService ticketService) {
        this.seatService = seatService;
        this.paymentService = paymentService;
        this.ticketService = ticketService;
    }

    @Override

    public long createTicket(int performanceId, LocalDateTime time, int seatNumber, String paymentStrategy) {
        return 0;
    }

    @Override
    public long createUserTicket(String userId) {
        return 0;
    }

    @Override
    public boolean checkTicket(long ticketId) {
        return false;
    }

    @Override
    public boolean checkToken(long ticketId, String token) {
        return false;
    }
}
