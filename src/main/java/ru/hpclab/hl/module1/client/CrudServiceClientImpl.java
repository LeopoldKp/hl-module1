package ru.hpclab.hl.module1.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightDTO;

import java.util.List;

@Component
public class CrudServiceClientImpl implements CrudServiceClient {
    private final RestTemplate restTemplate;
    private final String crudServiceUrl = "http://crud-service:8080";

    public CrudServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FlightDTO> searchFlights(String date, String departure, String destination) {
        String url = crudServiceUrl + "/flights/search?date=" + date;
        if (departure != null) {
            url += "&departure=" + departure;
        }
        if (destination != null) {
            url += "&destination=" + destination;
        }

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FlightDTO>>() {}
        ).getBody();
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return restTemplate.exchange(
                crudServiceUrl + "/bookings",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookingDTO>>() {}
        ).getBody();
    }
}