package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.cache.FlightCache;
import ru.hpclab.hl.module1.client.CrudServiceClient;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final CrudServiceClient crudServiceClient;
    private final FlightCache flightCache;

    public AvailabilityService(CrudServiceClient crudServiceClient,
                               FlightCache flightCache) {
        this.crudServiceClient = crudServiceClient;
        this.flightCache = flightCache;
    }

    public List<FlightAvailabilityResponse> getFlightAvailability(
            String departure,
            String destination,
            String date) {

        List<BookingDTO> allBookings = crudServiceClient.getAllBookings();

        Set<Long> uniqueFlightIds = allBookings.stream()
                .map(BookingDTO::getFlightId)
                .collect(Collectors.toSet());

        return uniqueFlightIds.stream()
                .map(flightId -> {
                    // Пытаемся получить рейс из кэша
                    FlightDTO flight = flightCache.get(flightId);

                    // Если нет в кэше - запрашиваем и сохраняем
                    if (flight == null) {
                        flight = crudServiceClient.getFlightById(flightId);
                        if (flight != null) {
                            flightCache.put(flightId, flight);
                        }
                    }

                    if (flight != null && matchesCriteria(flight, departure, destination, date)) {
                        int bookedSeats = (int) allBookings.stream()
                                .filter(booking -> booking.getFlightId().equals(flightId))
                                .count();

                        return new FlightAvailabilityResponse(
                                flight.getFlightNumber(),
                                flight.getDeparture(),
                                flight.getDestination(),
                                flight.getDepartureDate(),
                                flight.getCapacity() - bookedSeats
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
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

        return matches;
    }
}