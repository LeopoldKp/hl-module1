package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hpclab.hl.module1.entity.BookingEntity;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("SELECT COUNT(b) FROM BookingEntity b WHERE b.flight.id = :flightId")
    int countByFlightId(Long flightId);

    List<BookingEntity> findByFlightId(Long flightId);
}