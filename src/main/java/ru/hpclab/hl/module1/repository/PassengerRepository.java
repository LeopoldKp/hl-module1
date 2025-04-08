package ru.hpclab.hl.module1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Passenger;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PassengerRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Passenger> rowMapper = (rs, rowNum) -> {
        Passenger passenger = new Passenger();
        passenger.setId(UUID.fromString(rs.getString("id")));
        passenger.setFullName(rs.getString("full_name"));
        passenger.setPassportData(rs.getString("passport_data"));
        passenger.setContactInfo(rs.getString("contact_info"));
        return passenger;
    };

    public PassengerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTable();
    }

    private void initTable() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS passengers (
                id UUID PRIMARY KEY,
                full_name VARCHAR(255) NOT NULL,
                passport_data VARCHAR(255) NOT NULL UNIQUE,
                contact_info VARCHAR(255) NOT NULL
            )
            """);
    }

    public List<Passenger> findAll() {
        return jdbcTemplate.query("SELECT * FROM passengers", rowMapper);
    }

    public Optional<Passenger> findById(UUID id) {
        List<Passenger> passengers = jdbcTemplate.query(
                "SELECT * FROM passengers WHERE id = ?",
                rowMapper,
                id.toString()
        );
        return passengers.isEmpty() ? Optional.empty() : Optional.of(passengers.get(0));
    }

    public Passenger save(Passenger passenger) {
        if (passenger.getId() == null) {
            passenger.setId(UUID.randomUUID());
            jdbcTemplate.update(
                    "INSERT INTO passengers (id, full_name, passport_data, contact_info) VALUES (?, ?, ?, ?)",
                    passenger.getId().toString(),
                    passenger.getFullName(),
                    passenger.getPassportData(),
                    passenger.getContactInfo()
            );
        } else {
            jdbcTemplate.update(
                    "UPDATE passengers SET full_name = ?, passport_data = ?, contact_info = ? WHERE id = ?",
                    passenger.getFullName(),
                    passenger.getPassportData(),
                    passenger.getContactInfo(),
                    passenger.getId().toString()
            );
        }
        return passenger;
    }

    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM passengers WHERE id = ?", id.toString());
    }

    public Optional<Passenger> findByPassportData(String passportData) {
        List<Passenger> passengers = jdbcTemplate.query(
                "SELECT * FROM passengers WHERE passport_data = ?",
                rowMapper,
                passportData
        );
        return passengers.isEmpty() ? Optional.empty() : Optional.of(passengers.get(0));
    }

    public boolean existsByPassportData(String passportData) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM passengers WHERE passport_data = ?",
                Integer.class,
                passportData
        );
        return count != null && count > 0;
    }
}