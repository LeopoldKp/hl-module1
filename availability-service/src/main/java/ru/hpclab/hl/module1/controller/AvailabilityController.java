package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.service.AvailabilityService;
import ru.hpclab.hl.module1.dto.FlightAvailabilityResponse; // Правильный импорт

import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public List<FlightAvailabilityResponse> getAvailability( // Исправлен тип возвращаемого значения
                                                             @RequestParam(required = false) String departure,
                                                             @RequestParam String destination,
                                                             @RequestParam String date) {

        return availabilityService.getFlightAvailability(
                departure,
                destination,
                date
        );
    }
}