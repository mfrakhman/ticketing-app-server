package com.mfrakhman.etick.booking.service.impl;

import com.mfrakhman.etick.booking.dto.BookingRequest;
import com.mfrakhman.etick.booking.dto.BookingResponse;
import com.mfrakhman.etick.booking.entity.Booking;
import com.mfrakhman.etick.booking.mapper.BookingMapper;
import com.mfrakhman.etick.booking.repository.BookingRepository;
import com.mfrakhman.etick.booking.service.BookingService;
import com.mfrakhman.etick.ticket.entity.Ticket;
import com.mfrakhman.etick.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, TicketRepository ticketRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Booking booking = bookingMapper.toEntity(request, ticket);
        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toDto(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
