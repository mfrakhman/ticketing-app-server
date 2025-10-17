package com.mfrakhman.etick.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private LocalDateTime bookingTime;
    private Long ticketId;
}
