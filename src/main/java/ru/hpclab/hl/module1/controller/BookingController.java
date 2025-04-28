package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.service.BookingService;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final ObservabilityService observabilityService;

    public BookingController(BookingService bookingService,
                             ObservabilityService observabilityService) {
        this.bookingService = bookingService;
        this.observabilityService = observabilityService;
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        observabilityService.start("createBooking");
        try {
            BookingDTO created = bookingService.createBooking(bookingDTO);
            observabilityService.recordCustomMetric("bookingsCreated", 1);
            return created;
        } finally {
            observabilityService.stop("createBooking");
        }
    }

    @GetMapping("/flight/{flightId}")
    public List<BookingDTO> getBookingsByFlight(@PathVariable Long flightId) {
        observabilityService.start("getBookingsByFlight");
        try {
            List<BookingDTO> bookings = bookingService.getBookingsByFlight(flightId);
            observabilityService.recordCustomMetric("bookingsRetrieved", bookings.size());
            return bookings;
        } finally {
            observabilityService.stop("getBookingsByFlight");
        }
    }

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        observabilityService.start("getAllBookings");
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            observabilityService.recordCustomMetric("totalBookingsRetrieved", bookings.size());
            return bookings;
        } finally {
            observabilityService.stop("getAllBookings");
        }
    }
}