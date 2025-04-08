// FlightRepository.java
package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Flight;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class FlightRepository {
    private final Map<UUID, Flight> flights = new HashMap<>();

    public List<Flight> findAll() {
        return new ArrayList<>(flights.values());
    }

    public Flight save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
        return flight;
    }

    public Optional<Flight> findById(UUID flightNumber) {
        return Optional.ofNullable(flights.get(flightNumber));
    }

    public void delete(UUID flightNumber) {
        flights.remove(flightNumber);
    }

    // Новый метод для поиска рейсов по направлению и временному диапазону
    public List<Flight> findByDestinationAndDepartureTimeBetween(
            String destination,
            LocalDateTime start,
            LocalDateTime end) {

        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights.values()) {
            if (flight.getDestination().equalsIgnoreCase(destination) &&
                    !flight.getDepartureTime().isBefore(start) &&
                    flight.getDepartureTime().isBefore(end)) {
                result.add(flight);
            }
        }
        return result;
    }
}