package ru.hpclab.hl.module1.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;

@Component
public class CrudServiceClientImpl implements CrudServiceClient {
    private final RestTemplate restTemplate;
    private final ObservabilityService observabilityService;
    private final String crudServiceUrl = "http://aviation-main-internal:8080";

    public CrudServiceClientImpl(RestTemplate restTemplate,
                                 ObservabilityService observabilityService) {
        this.restTemplate = restTemplate;
        this.observabilityService = observabilityService;
    }

    @Override
    public FlightDTO getFlightById(Long id) {
        observabilityService.start("getFlightByIdFromCrud");
        try {
            FlightDTO result = restTemplate.getForObject(
                    crudServiceUrl + "/flights/" + id,
                    FlightDTO.class
            );
            observabilityService.recordCustomMetric("crudFlightRequests", 1);
            return result;
        } finally {
            observabilityService.stop("getFlightByIdFromCrud");
        }
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        observabilityService.start("getAllBookingsFromCrud");
        try {
            List<BookingDTO> result = restTemplate.exchange(
                    crudServiceUrl + "/bookings",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<BookingDTO>>() {}
            ).getBody();
            observabilityService.recordCustomMetric("crudBookingRequests", 1);
            return result;
        } finally {
            observabilityService.stop("getAllBookingsFromCrud");
        }
    }
}