// BookingService.java
package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Booking;
import ru.hpclab.hl.module1.model.Flight;
import ru.hpclab.hl.module1.model.Passenger;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.repository.FlightRepository;
import ru.hpclab.hl.module1.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public Booking createBooking(Booking booking) {
        // Проверка наличия свободных мест
        Flight flight = flightRepository.findById(booking.getFlightNumber())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats on this flight");
        }

        flight.setBookedSeats(flight.getBookedSeats() + 1);
        flightRepository.save(flight);

        return bookingRepository.save(booking);
    }

    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Flight flight = flightRepository.findById(booking.getFlightNumber())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setBookedSeats(flight.getBookedSeats() - 1);
        flightRepository.save(flight);

        bookingRepository.delete(bookingId);
    }

    public int getAvailableSeats(UUID flightNumber) {
        return flightRepository.findById(flightNumber)
                .map(Flight::getAvailableSeats)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}