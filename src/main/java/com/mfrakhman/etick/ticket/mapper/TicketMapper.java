package com.mfrakhman.etick.ticket.mapper;

import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.ticket.dto.TicketRequestDto;
import com.mfrakhman.etick.ticket.dto.TicketResponseDto;
import com.mfrakhman.etick.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public Ticket toEntity(TicketRequestDto dto, Event event) {
        Ticket ticket = new Ticket();
        ticket.setTicketName(dto.getTicketName());
        ticket.setQuantity(dto.getQuantity());
        ticket.setPrice(dto.getPrice());
        ticket.setEvent(event);
        return ticket;
    }

    public TicketResponseDto toDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        dto.setTicketName(ticket.getTicketName());
        dto.setQuantity(ticket.getQuantity());
        dto.setPrice(ticket.getPrice());
        dto.setEventId(ticket.getEvent() != null ? ticket.getEvent().getId() : null);
        return dto;
    }
}
