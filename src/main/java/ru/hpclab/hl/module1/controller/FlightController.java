package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.FlightService;

import java.time.LocalDate;
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

    @GetMapping("/availability")
    @Operation(summary = "Получить список рейсов с количеством свободных мест")
    public List<FlightAvailability> getFlightsAvailability(
            @Parameter(description = "Город отправления (опционально)") @RequestParam(required = false) String from,
            @Parameter(description = "Город назначения (опционально)") @RequestParam(required = false) String to,
            @Parameter(description = "Дата в формате yyyy-MM-dd", required = true) @RequestParam String date) {

        return flightService.getFlightsAvailability(from, to, date);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск рейсов по параметрам")
    public List<FlightDTO> searchFlights(
            @Parameter(description = "Город отправления (опционально)") @RequestParam(required = false) String departure,
            @Parameter(description = "Город назначения (опционально)") @RequestParam(required = false) String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd") @RequestParam String date) {

        return flightService.searchFlights(departure, destination, date);
    }

    @GetMapping("/by-destination")
    @Operation(summary = "Поиск рейсов по городу назначения и дате")
    public List<FlightWithSeats> getFlightsByDestinationAndDate(
            @Parameter(description = "Город назначения") @RequestParam String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd") @RequestParam String date) {

        return flightService.getFlightsByDestinationAndDate(destination, date);
    }

    @GetMapping("/available-seats/{flightId}")
    @Operation(summary = "Получить количество свободных мест на конкретный рейс")
    public int getAvailableSeatsForFlight(
            @Parameter(description = "ID рейса") @PathVariable Long flightId) {
        return flightService.getAvailableSeatsForFlight(flightId);
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Очистить все рейсы")
    public void clearAllFlights() {
        flightService.clearAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый рейс")
    public FlightDTO createFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.createFlight(flightDTO);
    }

    public static class FlightAvailability {
        private final String flightNumber;
        private final String departure;
        private final String destination;
        private final LocalDate departureDate;
        private final int availableSeats;

        public FlightAvailability(String flightNumber, String departure, String destination,
                                  LocalDate departureDate, int availableSeats) {
            this.flightNumber = flightNumber;
            this.departure = departure;
            this.destination = destination;
            this.departureDate = departureDate;
            this.availableSeats = availableSeats;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public String getDeparture() {
            return departure;
        }

        public String getDestination() {
            return destination;
        }

        public LocalDate getDepartureDate() {
            return departureDate;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }
    }

    public static class FlightWithSeats {
        private final String flightNumber;
        private final String departure;
        private final LocalDate departureDate;
        private final int availableSeats;

        public FlightWithSeats(String flightNumber, String departure,
                               LocalDate departureDate, int availableSeats) {
            this.flightNumber = flightNumber;
            this.departure = departure;
            this.departureDate = departureDate;
            this.availableSeats = availableSeats;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public String getDeparture() {
            return departure;
        }

        public LocalDate getDepartureDate() {
            return departureDate;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }
    }
}