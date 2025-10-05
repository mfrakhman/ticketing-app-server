package com.mfrakhman.etick.event.mapper;

import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
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
}
