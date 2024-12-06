package com.example.tickethub_producer.service;

import java.time.LocalDateTime;

public interface ProxyService {

    long createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken);

    boolean checkTicket(long ticketId, String jwtToken);

    boolean checkToken(long ticketId, String token, String jwtToken);
}
