package ru.hpclab.hl.module1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hpclab.hl.module1.entity.PassengerEntity;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
    PassengerEntity findByPassportNumber(String passportNumber);
}