package com.mfrakhman.etick.ticket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketRequestDto {
    @NotBlank(message = "Ticket name cannot be blank")
    private String ticketName;

    @NotNull(message = "Ticket quantity cannot be blank")
    @Min(value = 1, message = "Total ticket must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Ticket price cannot be blank")
    @Min(value = 1, message = "Ticket price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Event id cannot be blank")
    @Min(value = 1, message = "Event ID must be greater than 0")
    private Long eventId;
}
