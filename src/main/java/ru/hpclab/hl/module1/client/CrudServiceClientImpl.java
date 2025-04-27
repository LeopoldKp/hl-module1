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
    public FlightDTO getFlightById(Long id) {
        return restTemplate.getForObject(
                crudServiceUrl + "/flights/" + id,
                FlightDTO.class
        );
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