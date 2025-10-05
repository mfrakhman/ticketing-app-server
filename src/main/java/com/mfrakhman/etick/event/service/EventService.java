package com.mfrakhman.etick.event.service;

import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<EventResponse> getAllEvents();

    EventResponse getEventById(Long id);

    EventResponse createEvent(EventRequest request);

    EventResponse updateEvent(Long id, EventRequest request);

    void deleteEvent(Long id);
}
