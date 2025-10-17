package com.mfrakhman.etick.booking.mapper;

import com.mfrakhman.etick.booking.dto.BookingRequest;
import com.mfrakhman.etick.booking.dto.BookingResponse;
import com.mfrakhman.etick.booking.entity.Booking;
import com.mfrakhman.etick.ticket.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public Booking toEntity(BookingRequest dto, Ticket ticket) {
        Booking booking = new Booking();
        booking.setCustomerName(dto.getCustomerName());
        booking.setCustomerEmail(dto.getCustomerEmail());
        booking.setQuantity(dto.getQuantity());
        booking.setTicket(ticket);
        return booking;
    }

    public BookingResponse toDto(Booking booking) {
        BookingResponse dto = new BookingResponse();
        dto.setId(booking.getId());
        dto.setCustomerName(booking.getCustomerName());
        dto.setCustomerEmail(booking.getCustomerEmail());
        dto.setQuantity(booking.getQuantity());
        dto.setBookingTime(booking.getBookingTime());
        dto.setTicketId(booking.getTicket().getId());
        return dto;
    }
}
