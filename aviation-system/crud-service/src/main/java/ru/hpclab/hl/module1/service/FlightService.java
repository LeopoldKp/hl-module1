package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.entity.FlightEntity;
import ru.hpclab.hl.module1.mapper.FlightMapper;
import ru.hpclab.hl.module1.repository.FlightRepository;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.controller.FlightController.FlightAvailability;
import ru.hpclab.hl.module1.controller.FlightController.FlightWithSeats;

import java.time.LocalDate;
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

    public List<FlightAvailability> getFlightsAvailability(String departure, String destination,
                                                           LocalDateTime startDate, LocalDateTime endDate) {
        return flightRepository.findByCriteria(departure, destination, startDate, endDate).stream()
                .map(flight -> {
                    int bookedSeats = bookingRepository.countByFlightId(flight.getId());
                    int availableSeats = flight.getCapacity() - bookedSeats;
                    return new FlightAvailability(
                            flight.getFlightNumber(),
                            flight.getDeparture(),
                            flight.getDestination(),
                            flight.getDepartureTime().toLocalDate(),
                            availableSeats
                    );
                })
                .collect(Collectors.toList());
    }

    public List<FlightWithSeats> getFlightsByDestinationAndDate(String destination,
                                                                LocalDateTime startDate,
                                                                LocalDateTime endDate) {
        return flightRepository.findByDestinationAndDate(destination, startDate, endDate).stream()
                .map(flight -> {
                    int bookedSeats = bookingRepository.countByFlightId(flight.getId());
                    int availableSeats = flight.getCapacity() - bookedSeats;
                    return new FlightWithSeats(
                            flight.getFlightNumber(),
                            flight.getDeparture(),
                            flight.getDepartureTime().toLocalDate(),
                            availableSeats
                    );
                })
                .collect(Collectors.toList());
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

    public void clearAll() {
        flightRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        FlightEntity flightEntity = FlightMapper.toEntity(flightDTO);
        flightEntity = flightRepository.save(flightEntity);
        return FlightMapper.toDTO(flightEntity);
    }

    public List<FlightDTO> searchFlights(String departure, String destination,
                                         LocalDateTime startDate, LocalDateTime endDate) {
        return flightRepository.findByCriteria(departure, destination, startDate, endDate)
                .stream()
                .map(FlightMapper::toDTO)
                .collect(Collectors.toList());
    }
}