package ru.hpclab.hl.module1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.hpclab.hl.module1.model.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Booking> rowMapper = (rs, rowNum) -> {
        Booking booking = new Booking();
        booking.setId(UUID.fromString(rs.getString("id")));
        booking.setFlightNumber(UUID.fromString(rs.getString("flight_number")));
        booking.setPassengerId(UUID.fromString(rs.getString("passenger_id")));
        booking.setServiceClass(rs.getString("service_class"));
        booking.setSeatNumber(rs.getString("seat_number"));
        return booking;
    };

    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Booking> findAll() {
        return jdbcTemplate.query("SELECT * FROM bookings", rowMapper);
    }

    public Optional<Booking> findById(UUID id) {
        List<Booking> bookings = jdbcTemplate.query(
                "SELECT * FROM bookings WHERE id = ?",
                rowMapper,
                id.toString()
        );
        return bookings.isEmpty() ? Optional.empty() : Optional.of(bookings.get(0));
    }

    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(UUID.randomUUID());
            jdbcTemplate.update(
                    "INSERT INTO bookings (id, flight_number, passenger_id, service_class, seat_number) VALUES (?, ?, ?, ?, ?)",
                    booking.getId().toString(),
                    booking.getFlightNumber().toString(),
                    booking.getPassengerId().toString(),
                    booking.getServiceClass(),
                    booking.getSeatNumber()
            );
        } else {
            jdbcTemplate.update(
                    "UPDATE bookings SET flight_number = ?, passenger_id = ?, service_class = ?, seat_number = ? WHERE id = ?",
                    booking.getFlightNumber().toString(),
                    booking.getPassengerId().toString(),
                    booking.getServiceClass(),
                    booking.getSeatNumber(),
                    booking.getId().toString()
            );
        }
        return booking;
    }

    public void delete(UUID id) {
        jdbcTemplate.update(
                "DELETE FROM bookings WHERE id = ?",
                id.toString()
        );
    }

    public List<Booking> findByFlightNumber(UUID flightNumber) {
        return jdbcTemplate.query(
                "SELECT * FROM bookings WHERE flight_number = ?",
                rowMapper,
                flightNumber.toString()
        );
    }
}