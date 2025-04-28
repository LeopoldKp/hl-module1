package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.controller.FlightController;
import ru.hpclab.hl.module1.dto.FlightDTO;
import ru.hpclab.hl.module1.entity.FlightEntity;
import ru.hpclab.hl.module1.mapper.FlightMapper;
import ru.hpclab.hl.module1.repository.FlightRepository;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final ObservabilityService observabilityService;

    public FlightService(FlightRepository flightRepository,
                         BookingRepository bookingRepository,
                         ObservabilityService observabilityService) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.observabilityService = observabilityService;
    }

    public List<FlightDTO> getAllFlights() {
        observabilityService.start("getAllFlightsDB");
        try {
            return flightRepository.findAll().stream()
                    .map(FlightMapper::toDTO)
                    .collect(Collectors.toList());
        } finally {
            observabilityService.stop("getAllFlightsDB");
        }
    }

    public FlightDTO getFlightById(Long id) {
        observabilityService.start("getFlightByIdDB");
        try {
            return flightRepository.findById(id)
                    .map(flight -> {
                        FlightDTO dto = FlightMapper.toDTO(flight);
                        int bookedSeats = bookingRepository.countByFlightId(flight.getId());
                        dto.setAvailableSeats(flight.getCapacity() - bookedSeats);
                        return dto;
                    })
                    .orElse(null);
        } finally {
            observabilityService.stop("getFlightByIdDB");
        }
    }

    public List<FlightController.FlightAvailability> getFlightsAvailability(
            String from, String to, String date) {

        LocalDate flightDate = LocalDate.parse(date);
        return flightRepository.findByCriteria(from, to, flightDate).stream()
                .map(flight -> new FlightController.FlightAvailability(
                        flight.getFlightNumber(),
                        flight.getDeparture(),
                        flight.getDestination(),
                        flight.getDepartureDate()
                        // Убрали availableSeats
                ))
                .collect(Collectors.toList());
    }

    public List<FlightDTO> searchFlights(String departure, String destination, String date) {
        observabilityService.start("searchFlightsDB");
        try {
            LocalDate flightDate = LocalDate.parse(date);
            return flightRepository.findByCriteria(departure, destination, flightDate).stream()
                    .map(FlightMapper::toDTO)
                    .collect(Collectors.toList());
        } finally {
            observabilityService.stop("searchFlightsDB");
        }
    }

    public List<FlightController.FlightWithSeats> getFlightsByDestinationAndDate(
            String destination, String date) {

        LocalDate flightDate = LocalDate.parse(date);
        return flightRepository.findByDestinationAndDate(destination, flightDate).stream()
                .map(flight -> new FlightController.FlightWithSeats(
                        flight.getFlightNumber(),
                        flight.getDeparture(),
                        flight.getDepartureDate()
                        // Убрали availableSeats
                ))
                .collect(Collectors.toList());
    }

    public int getAvailableSeatsForFlight(Long flightId) {
        observabilityService.start("getAvailableSeatsDB");
        try {
            FlightEntity flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new RuntimeException("Flight not found"));
            int bookedSeats = bookingRepository.countByFlightId(flightId);
            return flight.getCapacity() - bookedSeats;
        } finally {
            observabilityService.stop("getAvailableSeatsDB");
        }
    }

    public void clearAll() {
        observabilityService.start("clearAllFlightsDB");
        try {
            flightRepository.deleteAll();
        } finally {
            observabilityService.stop("clearAllFlightsDB");
        }
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        observabilityService.start("createFlightDB");
        try {
            FlightEntity entity = FlightMapper.toEntity(flightDTO);
            entity = flightRepository.save(entity);
            return FlightMapper.toDTO(entity);
        } finally {
            observabilityService.stop("createFlightDB");
        }
    }
}