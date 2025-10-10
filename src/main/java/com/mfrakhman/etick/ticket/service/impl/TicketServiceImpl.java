package com.mfrakhman.etick.ticket.service.impl;

import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.event.repository.EventRepository;
import com.mfrakhman.etick.ticket.dto.TicketRequestDto;
import com.mfrakhman.etick.ticket.dto.TicketResponseDto;
import com.mfrakhman.etick.ticket.entity.Ticket;
import com.mfrakhman.etick.ticket.mapper.TicketMapper;
import com.mfrakhman.etick.ticket.repository.TicketRepository;
import com.mfrakhman.etick.ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketResponseDto createTicket(TicketRequestDto dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Ticket ticket = ticketMapper.toEntity(dto, event);
        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toDto(savedTicket);
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.toDto(ticket);
    }

    @Override
    public TicketResponseDto updateTicket(Long id, TicketRequestDto requestDto) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        existingTicket.setTicketName(requestDto.getTicketName());
        existingTicket.setPrice(requestDto.getPrice());

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return ticketMapper.toDto(updatedTicket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
