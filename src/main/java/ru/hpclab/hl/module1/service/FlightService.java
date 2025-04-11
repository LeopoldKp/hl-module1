package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.entity.FlightEntity;
import ru.hpclab.hl.module1.mapper.FlightMapper;
import ru.hpclab.hl.module1.repository.FlightRepository;
import ru.hpclab.hl.module1.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    public FlightService(FlightRepository flightRepository, BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::enrichWithAvailableSeats)
                .collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::enrichWithAvailableSeats)
                .orElse(null);
    }

    public List<FlightDTO> findFlights(String departure, String destination, LocalDateTime date) {
        LocalDateTime startDate = date.withHour(0).withMinute(0);
        LocalDateTime endDate = date.withHour(23).withMinute(59);

        return flightRepository.findByRouteAndDate(departure, destination, startDate, endDate).stream()
                .map(this::enrichWithAvailableSeats)
                .collect(Collectors.toList());
    }

    public int getAvailableSeatsForRouteAndDate(String departure, String destination, LocalDateTime date) {
        LocalDateTime startDate = date.withHour(0).withMinute(0);
        LocalDateTime endDate = date.withHour(23).withMinute(59);

        List<FlightEntity> flights = flightRepository.findByRouteAndDate(departure, destination, startDate, endDate);

        return flights.stream()
                .mapToInt(flight -> {
                    int bookedSeats = bookingRepository.countByFlightId(flight.getId());
                    return flight.getCapacity() - bookedSeats;
                })
                .sum();
    }

    public int getAvailableSeatsForFlight(Long flightId) {
        FlightEntity flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        int bookedSeats = bookingRepository.countByFlightId(flightId);
        return flight.getCapacity() - bookedSeats;
    }

    private FlightDTO enrichWithAvailableSeats(FlightEntity flight) {
        int bookedSeats = bookingRepository.countByFlightId(flight.getId());
        FlightDTO dto = FlightMapper.toDTO(flight);
        dto.setAvailableSeats(flight.getCapacity() - bookedSeats);
        return dto;
    }
}