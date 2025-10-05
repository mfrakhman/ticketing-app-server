package com.mfrakhman.etick.event.service.impl;

import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.event.mapper.EventMapper;
import com.mfrakhman.etick.event.repository.EventRepository;
import com.mfrakhman.etick.event.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
        event.setEventName(request.getEventName());
        event.setEventDate(request.getEventDate());
        event.setEventVenue(request.getEventVenue());
        event.setEventPax(request.getEventPax());
        eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
