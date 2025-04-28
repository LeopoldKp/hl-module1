package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.service.AvailabilityService;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final ObservabilityService observabilityService;

    public AvailabilityController(AvailabilityService availabilityService,
                                  ObservabilityService observabilityService) {
        this.availabilityService = availabilityService;
        this.observabilityService = observabilityService;
    }

    @GetMapping
    public List<FlightAvailabilityResponse> getAvailability(
            @RequestParam(required = false) String departure,
            @RequestParam String destination,
            @RequestParam String date) {

        observabilityService.start("getAvailability");
        try {
            List<FlightAvailabilityResponse> result = availabilityService.getFlightAvailability(
                    departure,
                    destination,
                    date
            );
            observabilityService.recordCustomMetric("availabilityRequests", 1);
            return result;
        } finally {
            observabilityService.stop("getAvailability");
        }
    }
}