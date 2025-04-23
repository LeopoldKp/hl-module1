package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hpclab.hl.module1.entity.FlightEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {

    @Query("SELECT f FROM FlightEntity f WHERE " +
            "(f.departure = :departure OR :departure IS NULL) AND " +
            "(f.destination = :destination OR :destination IS NULL) AND " +
            "f.departureTime BETWEEN :startDate AND :endDate " +
            "ORDER BY f.departureTime")
    List<FlightEntity> findByCriteria(String departure,
                                      String destination,
                                      LocalDateTime startDate,
                                      LocalDateTime endDate);

    @Query("SELECT f FROM FlightEntity f WHERE f.destination = :destination " +
            "AND f.departureTime BETWEEN :startDate AND :endDate " +
            "ORDER BY f.departureTime")
    List<FlightEntity> findByDestinationAndDate(String destination,
                                                LocalDateTime startDate,
                                                LocalDateTime endDate);
}