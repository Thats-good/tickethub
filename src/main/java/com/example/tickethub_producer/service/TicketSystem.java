package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.entity.User;
import com.example.tickethub_producer.repository.TicketRepository;
import com.example.tickethub_producer.repository.UserRepository;
import com.example.tickethub_producer.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketSystem implements ProxyService {

    //private final TicketService ticketService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TicketRepository ticketRepository;

    @Override
    public long createTicket(long userId, long performanceId, LocalDateTime time, int seatNumber, String payment, String jwtToken) {
//        ProduceTicketResponse ticket = ticketService.createTicket(userId, performanceId, time, seatNumber, payment);
        return 0;
    }

    @Override
    public List<Ticket> checkUserTicket(long userId, String jwtToken) {
        User user = userRepository.findById(userId).get();
        return ticketRepository.findAllByUser(user);
    }

    @Override
    public String checkTicket(long ticketId, String jwtToken) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        String ticketToken = jwtProvider.createTicketToken(ticket);
        ticket.setToken(ticketToken);
        ticketRepository.save(ticket);
        return ticketToken;
    }

    @Override
    public boolean checkToken(long ticketId, String token, String jwtToken) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        return ticket.getToken().equals(token) && jwtProvider.isExpiredToken(token);
    }
}
