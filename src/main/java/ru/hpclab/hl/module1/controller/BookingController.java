// BookingController.java
package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Booking;
import ru.hpclab.hl.module1.service.BookingService;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable UUID bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/available-seats/{flightNumber}")
    public int getAvailableSeats(@PathVariable UUID flightNumber) {
        return bookingService.getAvailableSeats(flightNumber);
    }
}