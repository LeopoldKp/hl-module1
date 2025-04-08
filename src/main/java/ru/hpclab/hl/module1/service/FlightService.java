// FlightService.java
package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Flight;
import ru.hpclab.hl.module1.repository.FlightRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(UUID flightNumber) {
        return flightRepository.findById(flightNumber);
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(UUID flightNumber) {
        flightRepository.delete(flightNumber);
    }

    // Новый метод для поиска рейсов по направлению и дате
    public List<Flight> getFlightsByDestinationAndDate(String destination, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return flightRepository.findByDestinationAndDepartureTimeBetween(destination, startOfDay, endOfDay);
    }
}