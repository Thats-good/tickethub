package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    Ticket createTicket(int performanceId, LocalDateTime time, int seatNumber);

    Long saveTicket();

    List<Ticket> checkUserTicket(Long userId);

    Ticket checkTicket(Long ticketId);

    boolean checkToken(Long ticketId, String token);

}
