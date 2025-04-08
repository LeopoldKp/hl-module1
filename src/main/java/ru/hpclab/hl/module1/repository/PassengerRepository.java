// PassengerRepository.java
package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Passenger;

import java.util.*;

@Repository
public class PassengerRepository {
    private final Map<UUID, Passenger> passengers = new HashMap<>();

    public List<Passenger> findAll() {
        return new ArrayList<>(passengers.values());
    }

    public Passenger save(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
        return passenger;
    }

    public Optional<Passenger> findById(UUID id) {
        return Optional.ofNullable(passengers.get(id));
    }

    public void delete(UUID id) {
        passengers.remove(id);
    }
}