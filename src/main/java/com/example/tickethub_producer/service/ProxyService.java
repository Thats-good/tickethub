package com.example.tickethub_producer.service;

import com.example.tickethub_producer.dto.ProduceTicketResponse;
import com.example.tickethub_producer.entity.PerformanceTicket;
import com.example.tickethub_producer.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface ProxyService {

    ProduceTicketResponse createTicket(long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken);

    List<PerformanceTicket> checkUserTicket(String jwtToken);

    String checkTicket(long ticketId, String jwtToken);

    boolean checkToken(long ticketId, String token, String jwtToken);
}
