package com.mfrakhman.etick.ticket.entity;

import com.mfrakhman.etick.booking.entity.Booking;
import com.mfrakhman.etick.event.entity.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private Long id;

    @Column(name = "ticket_name", nullable = false)
    private String ticketName;

    @Column(name = "total_ticket", nullable = false)
    private Integer totalTicket;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // Many tickets belong to one event
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;

    // One Ticket can have many Bookings
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;
}
