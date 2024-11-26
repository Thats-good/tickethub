package com.example.tickethub_producer.service;

import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public Ticket createTicket(int performanceId, LocalDateTime time, int seatNumber) {
        return null;
    }

    @Override
    public Long saveTicket() {
        return null;
    }

    @Override
    public List<Ticket> checkUserTicket(Long userId) {
        return null;
    }

    @Override
    public Ticket checkTicket(Long ticketId) {
        return null;
    }

    @Override
    public boolean checkToken(Long ticketId, String token) {
        return false;
    }

    private class Reservation {

        final TicketRepository ticketRepository;

        public Reservation(TicketRepository ticketRepository) {
            this.ticketRepository = ticketRepository;
        }

        Ticket ticket;

        Ticket createTicket(int performanceId, LocalDateTime time, int SeatNumber) {
            return null;
        }

        Long saveTicket() {
            return 0L;
        }

    }

    private class TokenIssue {

        String generateToken() {
            return null;
        }

        void delete() {

        }
    }
}
