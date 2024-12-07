package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface ProxyService {

    long createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken);

    List<Ticket> checkUserTicket(long userId, String jwtToken);

    String checkTicket(long ticketId, String jwtToken);

    boolean checkToken(long ticketId, String token, String jwtToken);
}
