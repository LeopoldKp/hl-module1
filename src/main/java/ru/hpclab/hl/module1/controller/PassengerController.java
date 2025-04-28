package ru.hpclab.hl.module1.controller;

import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.service.PassengerService;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;
    private final ObservabilityService observabilityService;

    public PassengerController(PassengerService passengerService,
                               ObservabilityService observabilityService) {
        this.passengerService = passengerService;
        this.observabilityService = observabilityService;
    }

    @GetMapping
    public List<PassengerDTO> getAllPassengers() {
        observabilityService.start("getAllPassengers");
        try {
            List<PassengerDTO> passengers = passengerService.getAllPassengers();
            observabilityService.recordCustomMetric("passengersRetrieved", passengers.size());
            return passengers;
        } finally {
            observabilityService.stop("getAllPassengers");
        }
    }

    @GetMapping("/{id}")
    public PassengerDTO getPassengerById(@PathVariable Long id) {
        observabilityService.start("getPassengerById");
        try {
            return passengerService.getPassengerById(id);
        } finally {
            observabilityService.stop("getPassengerById");
        }
    }

    @PostMapping
    public PassengerDTO createPassenger(@RequestBody PassengerDTO passengerDTO) {
        observabilityService.start("createPassenger");
        try {
            PassengerDTO created = passengerService.createOrUpdatePassenger(passengerDTO);
            observabilityService.recordCustomMetric("passengersCreated", 1);
            return created;
        } finally {
            observabilityService.stop("createPassenger");
        }
    }

    @DeleteMapping("/clear")
    public void clearAllPassengers() {
        observabilityService.start("clearAllPassengers");
        try {
            passengerService.clearAll();
            observabilityService.recordCustomMetric("passengersCleared", 1);
        } finally {
            observabilityService.stop("clearAllPassengers");
        }
    }
}