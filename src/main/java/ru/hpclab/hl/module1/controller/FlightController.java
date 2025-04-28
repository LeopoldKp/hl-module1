package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.FlightService;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
@Tag(name = "Flight Management", description = "Управление рейсами")
public class FlightController {
    private final FlightService flightService;
    private final ObservabilityService observabilityService;

    public FlightController(FlightService flightService,
                            ObservabilityService observabilityService) {
        this.flightService = flightService;
        this.observabilityService = observabilityService;
    }

    @GetMapping
    @Operation(summary = "Получить все рейсы")
    public List<FlightDTO> getAllFlights() {
        observabilityService.start("getAllFlights");
        try {
            List<FlightDTO> flights = flightService.getAllFlights();
            observabilityService.recordCustomMetric("flightsRetrieved", flights.size());
            return flights;
        } finally {
            observabilityService.stop("getAllFlights");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить рейс по ID")
    public FlightDTO getFlightById(@PathVariable Long id) {
        observabilityService.start("getFlightById");
        try {
            return flightService.getFlightById(id);
        } finally {
            observabilityService.stop("getFlightById");
        }
    }

    @GetMapping("/availability")
    @Operation(summary = "Получить список рейсов с количеством свободных мест")
    public List<FlightAvailability> getFlightsAvailability(
            @Parameter(description = "Город отправления (опционально)") @RequestParam(required = false) String from,
            @Parameter(description = "Город назначения (опционально)") @RequestParam(required = false) String to,
            @Parameter(description = "Дата в формате yyyy-MM-dd", required = true) @RequestParam String date) {

        observabilityService.start("getFlightsAvailability");
        try {
            List<FlightAvailability> result = flightService.getFlightsAvailability(from, to, date);
            observabilityService.recordCustomMetric("availabilityChecks", result.size());
            return result;
        } finally {
            observabilityService.stop("getFlightsAvailability");
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск рейсов по параметрам")
    public List<FlightDTO> searchFlights(
            @Parameter(description = "Город отправления (опционально)") @RequestParam(required = false) String departure,
            @Parameter(description = "Город назначения (опционально)") @RequestParam(required = false) String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd") @RequestParam String date) {

        observabilityService.start("searchFlights");
        try {
            List<FlightDTO> flights = flightService.searchFlights(departure, destination, date);
            observabilityService.recordCustomMetric("flightSearches", flights.size());
            return flights;
        } finally {
            observabilityService.stop("searchFlights");
        }
    }

    @GetMapping("/by-destination")
    @Operation(summary = "Поиск рейсов по городу назначения и дате")
    public List<FlightWithSeats> getFlightsByDestinationAndDate(
            @Parameter(description = "Город назначения") @RequestParam String destination,
            @Parameter(description = "Дата в формате yyyy-MM-dd") @RequestParam String date) {

        observabilityService.start("getFlightsByDestination");
        try {
            List<FlightWithSeats> flights = flightService.getFlightsByDestinationAndDate(destination, date);
            observabilityService.recordCustomMetric("destinationFlights", flights.size());
            return flights;
        } finally {
            observabilityService.stop("getFlightsByDestination");
        }
    }

    @GetMapping("/available-seats/{flightId}")
    @Operation(summary = "Получить количество свободных мест на конкретный рейс")
    public int getAvailableSeatsForFlight(
            @Parameter(description = "ID рейса") @PathVariable Long flightId) {
        observabilityService.start("getAvailableSeats");
        try {
            int seats = flightService.getAvailableSeatsForFlight(flightId);
            observabilityService.recordCustomMetric("availableSeatsChecked", 1);
            return seats;
        } finally {
            observabilityService.stop("getAvailableSeats");
        }
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Очистить все рейсы")
    public void clearAllFlights() {
        observabilityService.start("clearAllFlights");
        try {
            flightService.clearAll();
            observabilityService.recordCustomMetric("flightsCleared", 1);
        } finally {
            observabilityService.stop("clearAllFlights");
        }
    }

    @PostMapping
    @Operation(summary = "Создать новый рейс")
    public FlightDTO createFlight(@RequestBody FlightDTO flightDTO) {
        observabilityService.start("createFlight");
        try {
            FlightDTO created = flightService.createFlight(flightDTO);
            observabilityService.recordCustomMetric("flightsCreated", 1);
            return created;
        } finally {
            observabilityService.stop("createFlight");
        }
    }

    public static class FlightAvailability {
        private String flightNumber;
        private String departure;
        private String destination;
        private LocalDate departureDate;

        @Schema(hidden = true) // Скрываем в Swagger
        private int availableSeats;

        // Конструктор без availableSeats
        public FlightAvailability(String flightNumber, String departure,
                                  String destination, LocalDate departureDate) {
            this.flightNumber = flightNumber;
            this.departure = departure;
            this.destination = destination;
            this.departureDate = departureDate;
        }

        // Геттеры (без availableSeats)
        public String getFlightNumber() { return flightNumber; }
        public String getDeparture() { return departure; }
        public String getDestination() { return destination; }
        public LocalDate getDepartureDate() { return departureDate; }
    }

    public static class FlightWithSeats {
        private String flightNumber;
        private String departure;
        private LocalDate departureDate;

        @Schema(hidden = true) // Скрываем в Swagger
        private int availableSeats;

        // Конструктор без availableSeats
        public FlightWithSeats(String flightNumber, String departure,
                               LocalDate departureDate) {
            this.flightNumber = flightNumber;
            this.departure = departure;
            this.departureDate = departureDate;
        }

        // Геттеры (без availableSeats)
        public String getFlightNumber() { return flightNumber; }
        public String getDeparture() { return departure; }
        public LocalDate getDepartureDate() { return departureDate; }
    }
}
