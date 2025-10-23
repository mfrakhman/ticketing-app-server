package com.mfrakhman.etick.event.service.impl;

import com.mfrakhman.etick.event.dto.EventDetailResponse;
import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.event.mapper.EventMapper;
import com.mfrakhman.etick.event.repository.EventRepository;
import com.mfrakhman.etick.event.service.EventService;
import com.mfrakhman.etick.exception.ResourceNotFoundException;
import com.mfrakhman.etick.ticket.entity.Ticket;
import com.mfrakhman.etick.ticket.mapper.TicketMapper;
import com.mfrakhman.etick.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public EventDetailResponse getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        List<Ticket> tickets = ticketRepository.findByEventId(event.getId());
        EventDetailResponse eventResponse = eventMapper.toResponseDetail(event);
        eventResponse.setTickets(tickets.stream().map(ticketMapper::toDto).collect(Collectors.toList()));
        return eventResponse;
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        eventRepository.save(event);
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
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
