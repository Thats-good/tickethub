package com.example.tickethub_producer.repository;

import com.example.tickethub_producer.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByTicketIdAndToken(Long ticketId, String token);
}
