package com.mfrakhman.etick.event.mapper;

import com.mfrakhman.etick.event.dto.EventDetailResponse;
import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.ticket.mapper.TicketMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventMapper {
    private final TicketMapper ticketMapper;

    public EventMapper(TicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    public Event toEntity(EventRequest request) {
        Event event = new Event();
        event.setEventName(request.getEventName());
        event.setEventDate(request.getEventDate());
        event.setEventVenue(request.getEventVenue());
        event.setEventPax(request.getEventPax());
        return event;
    }

    public EventResponse toResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setEventName(event.getEventName());
        response.setEventDate(event.getEventDate());
        response.setEventVenue(event.getEventVenue());
        response.setEventPax(event.getEventPax());
        return response;
    }

    public EventDetailResponse toResponseDetail(Event event) {
        EventDetailResponse response = new EventDetailResponse();
        response.setId(event.getId());
        response.setEventName(event.getEventName());
        response.setEventDate(event.getEventDate());
        response.setEventVenue(event.getEventVenue());
        response.setEventPax(event.getEventPax());
        response.setTickets(event.getTickets().stream().map(ticketMapper::toDto).collect(Collectors.toList()));
        return response;
    }
}
