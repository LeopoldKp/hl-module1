package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.client.CrudServiceClient;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final CrudServiceClient crudServiceClient;

    public AvailabilityService(CrudServiceClient crudServiceClient) {
        this.crudServiceClient = crudServiceClient;
    }

    public List<FlightAvailabilityResponse> getFlightAvailability(
            String departure,
            String destination,
            String date) {

        // Получаем рейсы через клиент
        List<FlightDTO> flights = crudServiceClient.searchFlights(date, departure, destination);

        // Получаем бронирования через клиент
        List<BookingDTO> bookings = crudServiceClient.getAllBookings();

        // Формируем ответ
        return flights.stream()
                .map(flight -> {
                    long bookedSeats = bookings.stream()
                            .filter(b -> b.getFlightId().equals(flight.getId()))
                            .count();

                    return new FlightAvailabilityResponse(
                            flight.getFlightNumber(),
                            flight.getDeparture(),
                            flight.getDestination(),
                            flight.getDepartureDate(),
                            flight.getCapacity() - (int) bookedSeats
                    );
                })
                .collect(Collectors.toList());
    }
}