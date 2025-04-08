// PassengerService.java
package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Passenger;
import ru.hpclab.hl.module1.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(UUID id) {
        return passengerRepository.findById(id);
    }

    public Passenger createPassenger(Passenger passenger) {
        if (passenger.getId() == null) {
            passenger.setId(UUID.randomUUID());
        }
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(UUID id) {
        passengerRepository.delete(id);
    }

    public Passenger updatePassenger(UUID id, Passenger passenger) {
        passenger.setId(id);
        return passengerRepository.save(passenger);
    }
}