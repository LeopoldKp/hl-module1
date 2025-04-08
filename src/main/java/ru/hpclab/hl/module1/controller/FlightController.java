package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Flight;
import ru.hpclab.hl.module1.service.FlightService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flights")
@Tag(name = "Flight Management", description = "Endpoints for managing flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    @Operation(summary = "Get all flights")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{flightNumber}")
    @Operation(summary = "Get flight by ID")
    public Flight getFlightById(@PathVariable UUID flightNumber) {
        return flightService.getFlightById(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @PostMapping
    @Operation(summary = "Create new flight")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }

    @DeleteMapping("/{flightNumber}")
    @Operation(summary = "Delete flight")
    public void deleteFlight(@PathVariable UUID flightNumber) {
        flightService.deleteFlight(flightNumber);
    }

    @GetMapping("/availability")
    @Operation(summary = "Get available seats by destination and date")
    public Map<String, Object> getAvailableSeatsByDestinationAndDate(
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

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

    @GetMapping("/{flightNumber}/available-seats")
    @Operation(summary = "Get available seats count for specific flight")
    public int getAvailableSeats(@PathVariable UUID flightNumber) {
        return flightService.getAvailableSeatsCount(flightNumber);
    }
}