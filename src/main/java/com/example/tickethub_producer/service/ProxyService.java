package com.example.tickethub_producer.service;

import java.time.LocalDateTime;

public interface ProxyService {

    long createTicket(int performanceId, LocalDateTime time, int seatNumber, String paymentStrategy, String jwtToken);

    long createUserTicket(String userId, String jwtToken);

    boolean checkTicket(long ticketId, String jwtToken);

    boolean checkToken(long ticketId, String token, String jwtToken);
}
