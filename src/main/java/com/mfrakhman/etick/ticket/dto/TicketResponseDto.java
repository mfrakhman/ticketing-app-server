package com.mfrakhman.etick.ticket.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketResponseDto {
    private Long id;
    private String ticketName;
    private Integer quantity;
    private BigDecimal price;
    private Long eventId;
}
