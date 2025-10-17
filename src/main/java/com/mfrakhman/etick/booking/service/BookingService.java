package com.mfrakhman.etick.booking.service;

import com.mfrakhman.etick.booking.dto.BookingRequest;
import com.mfrakhman.etick.booking.dto.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);

    BookingResponse getBookingById(Long id);

    List<BookingResponse> getAllBookings();

    void deleteBooking(Long id);
}
