package ru.hpclab.hl.module1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FlightRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Flight> rowMapper = (rs, rowNum) -> {
        Flight flight = new Flight();
        flight.setFlightNumber(UUID.fromString(rs.getString("flight_number")));
        flight.setDestination(rs.getString("destination"));
        flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        flight.setCapacity(rs.getInt("capacity"));
        flight.setBookedSeats(rs.getInt("booked_seats"));
        return flight;
    };

    public FlightRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTable();
    }

    private void initTable() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS flights (
                flight_number UUID PRIMARY KEY,
                destination VARCHAR(255) NOT NULL,
                departure_time TIMESTAMP NOT NULL,
                capacity INT NOT NULL,
                booked_seats INT NOT NULL DEFAULT 0
            )
            """);
    }

    public List<Flight> findAll() {
        return jdbcTemplate.query("SELECT * FROM flights", rowMapper);
    }

    public Optional<Flight> findById(UUID flightNumber) {
        List<Flight> flights = jdbcTemplate.query(
                "SELECT * FROM flights WHERE flight_number = ?",
                rowMapper,
                flightNumber.toString()
        );
        return flights.isEmpty() ? Optional.empty() : Optional.of(flights.get(0));
    }

    public Flight save(Flight flight) {
        if (flight.getFlightNumber() == null) {
            flight.setFlightNumber(UUID.randomUUID());
            jdbcTemplate.update(
                    "INSERT INTO flights (flight_number, destination, departure_time, capacity, booked_seats) VALUES (?, ?, ?, ?, ?)",
                    flight.getFlightNumber().toString(),
                    flight.getDestination(),
                    flight.getDepartureTime(),
                    flight.getCapacity(),
                    flight.getBookedSeats()
            );
        } else {
            jdbcTemplate.update(
                    "UPDATE flights SET destination = ?, departure_time = ?, capacity = ?, booked_seats = ? WHERE flight_number = ?",
                    flight.getDestination(),
                    flight.getDepartureTime(),
                    flight.getCapacity(),
                    flight.getBookedSeats(),
                    flight.getFlightNumber().toString()
            );
        }
        return flight;
    }

    public void delete(UUID flightNumber) {
        jdbcTemplate.update("DELETE FROM flights WHERE flight_number = ?", flightNumber.toString());
    }

    public List<Flight> findByDestinationAndDate(String destination, LocalDateTime startDate, LocalDateTime endDate) {
        return jdbcTemplate.query(
                "SELECT * FROM flights WHERE destination = ? AND departure_time BETWEEN ? AND ?",
                rowMapper,
                destination,
                startDate,
                endDate
        );
    }

    public int getAvailableSeats(UUID flightNumber) {
        Integer availableSeats = jdbcTemplate.queryForObject(
                "SELECT (capacity - booked_seats) FROM flights WHERE flight_number = ?",
                Integer.class,
                flightNumber.toString()
        );
        return availableSeats != null ? availableSeats : 0;
    }
}