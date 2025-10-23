package com.mfrakhman.etick.ticket.controller;

import com.mfrakhman.etick.response.Response;
import com.mfrakhman.etick.ticket.dto.TicketRequestDto;
import com.mfrakhman.etick.ticket.dto.TicketResponseDto;
import com.mfrakhman.etick.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ticket")
@RestController
@RequestMapping(path = "api/v1/tickets")
@Validated
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Response<TicketResponseDto>> createTicket(@Valid @RequestBody TicketRequestDto requestDto) {
        TicketResponseDto createdTicket = ticketService.createTicket(requestDto);
        return Response.created("Ticket successfully created", createdTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<TicketResponseDto>> getTicketById(@PathVariable @Min(value = 1, message = "Ticket Id must be greater than 0") Long id) {
        TicketResponseDto ticket = ticketService.getTicketById(id);
        return Response.success("Ticket fetched successfully", ticket);
    }

    @GetMapping
    public ResponseEntity<Response<List<TicketResponseDto>>> getAllTickets() {
        List<TicketResponseDto> tickets = ticketService.getAllTickets();
        return Response.success("Tickets fetched successfully", tickets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<TicketResponseDto>> updateTicket(
            @PathVariable @Min(value = 1, message = "Ticket Id must be greater than 0") Long id,
            @Valid @RequestBody TicketRequestDto requestDto
    ) {
        TicketResponseDto updatedTicket = ticketService.updateTicket(id, requestDto);
        return Response.success("Ticket successfully updated", updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteTicket(@PathVariable @Min(value = 1, message = "Ticket Id must be greater than 0") Long id) {
        ticketService.deleteTicket(id);
        return Response.success("Ticket successfully deleted", null);
    }
}
