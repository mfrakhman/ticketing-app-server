package com.mfrakhman.etick.event.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventResponse {
    private Long id;
    private String eventName;
    private LocalDate eventDate;
    private String eventVenue;
    private Integer eventPax;
}
