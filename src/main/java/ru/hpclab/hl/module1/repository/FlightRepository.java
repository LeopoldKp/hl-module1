package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hpclab.hl.module1.entity.FlightEntity;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    @Query("SELECT f FROM FlightEntity f WHERE " +
            "(f.departure = :departure OR :departure IS NULL) AND " +
            "(f.destination = :destination OR :destination IS NULL) AND " +
            "f.departureDate = :date " +
            "ORDER BY f.departureDate")
    List<FlightEntity> findByCriteria(String departure,
                                      String destination,
                                      LocalDate date);

    @Query("SELECT f FROM FlightEntity f WHERE f.destination = :destination " +
            "AND f.departureDate = :date " +
            "ORDER BY f.departureDate")
    List<FlightEntity> findByDestinationAndDate(String destination,
                                                LocalDate date);
}