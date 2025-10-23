package com.mfrakhman.etick.event.controller;

import com.mfrakhman.etick.event.dto.EventDetailResponse;
import com.mfrakhman.etick.event.dto.EventRequest;
import com.mfrakhman.etick.event.dto.EventResponse;
import com.mfrakhman.etick.event.service.EventService;
import com.mfrakhman.etick.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Event")
@RestController
@RequestMapping("/api/v1/events")
@Validated
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<Response<List<EventResponse>>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return Response.success("Events fetched successfully", events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<EventDetailResponse>> getEventById(@PathVariable @Min(value = 1, message = "Id must be greater than 0") Long id) {
        EventDetailResponse event = eventService.getEventById(id);
        return Response.success("Event fetched successfully", event);
    }

    @PostMapping
    public ResponseEntity<Response<EventResponse>> createEvent(@Valid @RequestBody EventRequest request) {
        EventResponse createdEvent = eventService.createEvent(request);
        return Response.success("Event created successfully", createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<EventResponse>> updateEvent(
            @PathVariable @Min(value = 1, message = "Id must be greater than 0") Long id,
            @Valid @RequestBody EventRequest request
    ) {
        EventResponse updatedEvent = eventService.updateEvent(id, request);
        return Response.success("Event updated successfully", updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteEvent(@PathVariable @Min(value = 1, message = "Id must be greater than 0") Long id) {
        eventService.deleteEvent(id);
        return Response.success("Event deleted successfully", null);
    }
}
