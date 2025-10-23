package com.mfrakhman.etick.ticket.repository;

import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventId(Long eventId);
}
