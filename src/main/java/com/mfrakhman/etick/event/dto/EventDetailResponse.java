package com.mfrakhman.etick.event.dto;

import com.mfrakhman.etick.ticket.dto.TicketResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EventDetailResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String eventName;

    @NotBlank
    private LocalDate eventDate;

    @NotBlank
    private String eventVenue;

    @NotBlank
    private Integer eventPax;

    @NotNull
    private List<TicketResponseDto> tickets;
}
