package ru.hpclab.hl.module1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.cache.FlightCache;
import ru.hpclab.hl.module1.client.CrudServiceClient;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    private final CrudServiceClient crudServiceClient;
    private final FlightCache flightCache;
    private final ObservabilityService observabilityService;

    public AvailabilityService(CrudServiceClient crudServiceClient,
                               FlightCache flightCache,
                               ObservabilityService observabilityService) {
        this.crudServiceClient = crudServiceClient;
        this.flightCache = flightCache;
        this.observabilityService = observabilityService;
    }

    public List<FlightAvailabilityResponse> getFlightAvailability(
            String departure,
            String destination,
            String date) {

        logger.info("Starting availability check for departure: {}, destination: {}, date: {}",
                departure, destination, date);

        observabilityService.start("getAllBookings");
        List<BookingDTO> allBookings;
        try {
            allBookings = crudServiceClient.getAllBookings();
            logger.debug("Retrieved {} bookings from CRUD service", allBookings.size());
        } finally {
            observabilityService.stop("getAllBookings");
        }

        Set<Long> uniqueFlightIds = allBookings.stream()
                .map(BookingDTO::getFlightId)
                .collect(Collectors.toSet());

        logger.info("Processing {} unique flight IDs", uniqueFlightIds.size());

        return uniqueFlightIds.stream()
                .map(flightId -> processFlight(flightId, departure, destination, date, allBookings))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private FlightAvailabilityResponse processFlight(Long flightId, String departure,
                                                     String destination, String date,
                                                     List<BookingDTO> allBookings) {
        logger.debug("Processing flight ID: {}", flightId);
        observabilityService.start("processFlight");
        try {
            FlightDTO flight = getFlightWithCache(flightId);

            if (flight != null && matchesCriteria(flight, departure, destination, date)) {
                int bookedSeats = countBookedSeats(flightId, allBookings);
                return buildResponse(flight, bookedSeats);
            }
            return null;
        } finally {
            observabilityService.stop("processFlight");
        }
    }

    private FlightDTO getFlightWithCache(Long flightId) {
        logger.debug("Checking cache for flight ID: {}", flightId);
        FlightDTO flight = flightCache.get(flightId);

        if (flight == null) {
            logger.debug("Cache miss, fetching flight ID: {} from CRUD service", flightId);
            observabilityService.start("getFlightFromCrud");
            try {
                flight = crudServiceClient.getFlightById(flightId);
                if (flight != null) {
                    logger.debug("Caching flight ID: {}", flightId);
                    flightCache.put(flightId, flight);
                }
            } finally {
                observabilityService.stop("getFlightFromCrud");
            }
        }
        return flight;
    }

    private int countBookedSeats(Long flightId, List<BookingDTO> allBookings) {
        return (int) allBookings.stream()
                .filter(booking -> booking.getFlightId().equals(flightId))
                .count();
    }

    private FlightAvailabilityResponse buildResponse(FlightDTO flight, int bookedSeats) {
        return new FlightAvailabilityResponse(
                flight.getFlightNumber(),
                flight.getDeparture(),
                flight.getDestination(),
                flight.getDepartureDate(),
                flight.getCapacity() - bookedSeats
        );
    }

    private boolean matchesCriteria(FlightDTO flight, String departure,
                                    String destination, String date) {
        LocalDate flightDate = flight.getDepartureDate();
        LocalDate requestedDate = LocalDate.parse(date);

        boolean matches = flightDate.equals(requestedDate);

        if (departure != null) {
            matches = matches && flight.getDeparture().equalsIgnoreCase(departure);
        }

        if (destination != null) {
            matches = matches && flight.getDestination().equalsIgnoreCase(destination);
        }

        logger.debug("Flight ID {} matches criteria: {}", flight.getId(), matches);
        return matches;
    }
}