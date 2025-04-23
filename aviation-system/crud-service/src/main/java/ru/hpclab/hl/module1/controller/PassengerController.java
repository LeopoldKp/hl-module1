package ru.hpclab.hl.module1.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.PassengerDTO;
import ru.hpclab.hl.module1.service.PassengerService;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<PassengerDTO> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public PassengerDTO getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @PostMapping
    public PassengerDTO createPassenger(@RequestBody PassengerDTO passengerDTO) {
        return passengerService.createOrUpdatePassenger(passengerDTO);
    }

        @DeleteMapping("/clear")
        @Operation(summary = "Очистить всех пассажиров")
        public void clearAllPassengers() {
            passengerService.clearAll();
        }

}