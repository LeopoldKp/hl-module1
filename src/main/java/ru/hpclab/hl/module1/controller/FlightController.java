// FlightController.java
package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Flight;
import ru.hpclab.hl.module1.service.FlightService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{flightNumber}")
    public Flight getFlightById(@PathVariable UUID flightNumber) {
        return flightService.getFlightById(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @DeleteMapping("/{flightNumber}")
    public void deleteFlight(@PathVariable UUID flightNumber) {
        flightService.deleteFlight(flightNumber);
    }

    // Новый метод для проверки свободных мест по направлению и дате
    @GetMapping("/availability")
    public Map<String, Object> getAvailableSeatsByDestinationAndDate(
            @RequestParam String destination,
            @RequestParam LocalDate date) {

        List<Flight> flights = flightService.getFlightsByDestinationAndDate(destination, date);

        int totalAvailableSeats = flights.stream()
                .mapToInt(Flight::getAvailableSeats)
                .sum();

        return Map.of(
                "destination", destination,
                "date", date.toString(),
                "totalAvailableSeats", totalAvailableSeats,
                "flights", flights.stream().map(flight -> Map.of(
                        "flightNumber", flight.getFlightNumber(),
                        "departureTime", flight.getDepartureTime(),
                        "availableSeats", flight.getAvailableSeats()
                )).collect(Collectors.toList())
        );
    }
}