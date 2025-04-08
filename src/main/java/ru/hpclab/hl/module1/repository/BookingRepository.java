// BookingRepository.java
package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Booking;

import java.util.*;

@Repository
public class BookingRepository {
    private final Map<UUID, Booking> bookings = new HashMap<>();
    private final Map<UUID, List<UUID>> flightPassengers = new HashMap<>();

    public Booking save(Booking booking) {
        bookings.put(booking.getId(), booking);

        flightPassengers.computeIfAbsent(booking.getFlightNumber(), k -> new ArrayList<>())
                .add(booking.getPassengerId());

        return booking;
    }

    public Optional<Booking> findById(UUID id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public void delete(UUID id) {
        Booking booking = bookings.remove(id);
        if (booking != null) {
            List<UUID> passengers = flightPassengers.get(booking.getFlightNumber());
            if (passengers != null) {
                passengers.remove(booking.getPassengerId());
            }
        }
    }

    public List<UUID> getPassengersForFlight(UUID flightNumber) {
        return flightPassengers.getOrDefault(flightNumber, Collections.emptyList());
    }
}