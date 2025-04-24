package ru.hpclab.hl.module1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    private final RestTemplate restTemplate;
    private final String crudServiceUrl = "http://crud-service:8080";

    public AvailabilityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FlightAvailabilityResponse> getFlightAvailability(
            String departure,
            String destination,
            String date) {

        // Формируем URL для запроса рейсов
        String url = crudServiceUrl + "/flights/search?date=" + date;
        if (departure != null) {
            url += "&departure=" + departure;
        }
        if (destination != null) {
            url += "&destination=" + destination;
        }

        // Получаем рейсы за указанную дату
        List<FlightDTO> flights = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FlightDTO>>() {}
        ).getBody();

        // Получаем все бронирования
        List<BookingDTO> bookings = restTemplate.exchange(
                crudServiceUrl + "/bookings",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookingDTO>>() {}
        ).getBody();

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