package com.mfrakhman.etick.event.entity;

import com.mfrakhman.etick.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long id;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "event_venue", nullable = false)
    private String eventVenue;

    @Column(name = "event_pax", nullable = false)
    private Integer eventPax;

    // One Event can have many Tickets
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
}
