package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.BookingDTO;
import ru.hpclab.hl.module1.entity.BookingEntity;
import ru.hpclab.hl.module1.entity.FlightEntity;
import ru.hpclab.hl.module1.entity.PassengerEntity;
import ru.hpclab.hl.module1.mapper.BookingMapper;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.repository.FlightRepository;
import ru.hpclab.hl.module1.repository.PassengerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        FlightEntity flight = flightRepository.findById(bookingDTO.getFlightId())
                .orElseThrow(() -> new RuntimeException("Рейс не найден"));

        PassengerEntity passenger = passengerRepository.findById(bookingDTO.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Пассажир не найден"));

        // Проверка доступности мест
        long bookedSeats = bookingRepository.countByFlightId(flight.getId());
        if (bookedSeats >= flight.getCapacity()) {
            throw new RuntimeException("Нет свободных мест на этот рейс");
        }

        BookingEntity booking = new BookingEntity();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setSeatClass(bookingDTO.getSeatClass());
        booking.setSeatNumber(bookingDTO.getSeatNumber());
        booking.setBookingTime(LocalDateTime.now());

        booking = bookingRepository.save(booking);
        return BookingMapper.toDTO(booking);
    }

    public List<BookingDTO> getBookingsByFlight(Long flightId) {
        return bookingRepository.findByFlightId(flightId).stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
    }
}