package com.example.tickethub_producer.repository;

import com.example.tickethub_producer.entity.PerformanceTicket;
import com.example.tickethub_producer.entity.Ticket;
import com.example.tickethub_producer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceTicketRepository extends JpaRepository<PerformanceTicket, Long> {

    boolean existsByTicketIdAndToken(Long ticketId, String token);
    List<PerformanceTicket> findAllByUser(User user);
}
