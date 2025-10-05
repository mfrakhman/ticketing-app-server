package com.mfrakhman.etick.booking.entity;

import com.mfrakhman.etick.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Many Bookings belong to one Ticket
    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false)
    private Ticket ticket;
}
