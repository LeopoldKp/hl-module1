package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@Tag(name = "Flight Management", description = "Управление рейсами")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    @Operation(summary = "Получить все рейсы")
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить рейс по ID")
    public FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск рейсов по маршруту и дате")
    public List<FlightDTO> searchFlights(
            @Parameter(description = "Город отправления") @RequestParam String departure,
            @Parameter(description = "Город назначения") @RequestParam String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd'T'HH:mm:ss") @RequestParam String date) {

        LocalDateTime departureDate = LocalDateTime.parse(date);
        return flightService.findFlights(departure, destination, departureDate);
    }

    @GetMapping("/available-seats")
    @Operation(summary = "Получить количество свободных мест на направление и дату")
    public int getAvailableSeats(
            @Parameter(description = "Город отправления") @RequestParam String departure,
            @Parameter(description = "Город назначения") @RequestParam String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd'T'HH:mm:ss") @RequestParam String date) {

        LocalDateTime departureDate = LocalDateTime.parse(date);
        return flightService.getAvailableSeatsForRouteAndDate(departure, destination, departureDate);
    }

    @GetMapping("/{flightId}/available-seats")
    @Operation(summary = "Получить количество свободных мест на конкретный рейс")
    public int getAvailableSeatsForFlight(
            @Parameter(description = "ID рейса") @PathVariable Long flightId) {
        return flightService.getAvailableSeatsForFlight(flightId);
    }
}