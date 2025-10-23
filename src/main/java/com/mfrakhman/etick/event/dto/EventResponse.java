package com.mfrakhman.etick.event.dto;

import com.mfrakhman.etick.ticket.dto.TicketResponseDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EventResponse {
    private Long id;
    private String eventName;
    private LocalDate eventDate;
    private String eventVenue;
    private Integer eventPax;
}
