package com.mfrakhman.etick.ticket.service;

import com.mfrakhman.etick.event.entity.Event;
import com.mfrakhman.etick.ticket.dto.TicketRequestDto;
import com.mfrakhman.etick.ticket.dto.TicketResponseDto;

import java.util.List;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto dto);

    List<TicketResponseDto> getAllTickets();

    TicketResponseDto getTicketById(Long id);

    TicketResponseDto updateTicket(Long id, TicketRequestDto requestDto);

    void deleteTicket(Long id);
}
