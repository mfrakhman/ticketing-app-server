package com.mfrakhman.etick.booking.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private Long ticketId;
}
