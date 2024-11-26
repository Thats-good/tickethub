package com.example.tickethub_producer.service;

import java.time.LocalDateTime;

public interface TicketSystem {

    long createTicket(int performanceId, LocalDateTime time, int seatNumber, String paymentStrategy);

    long createUserTicket(String userId);

    boolean checkTicket(long ticketId);

    boolean checkToken(long ticketId, String token);
}
