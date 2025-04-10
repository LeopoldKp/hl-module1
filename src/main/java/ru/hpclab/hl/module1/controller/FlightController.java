package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.FlightService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/search")
    public List<FlightDTO> searchFlights(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String date) {

        LocalDateTime departureDate = LocalDateTime.parse(date);
        return flightService.findFlights(departure, destination, departureDate);
    }
}