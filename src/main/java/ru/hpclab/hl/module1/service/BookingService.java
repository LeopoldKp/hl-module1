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
import ru.hpclab.hl.module1.service.statistics.ObservabilityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ObservabilityService observabilityService;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          PassengerRepository passengerRepository,
                          ObservabilityService observabilityService) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.observabilityService = observabilityService;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        observabilityService.start("createBookingDB");
        try {
            observabilityService.start("findFlightDB");
            FlightEntity flight;
            try {
                flight = flightRepository.findById(bookingDTO.getFlightId())
                        .orElseThrow(() -> new RuntimeException("Flight not found"));
            } finally {
                observabilityService.stop("findFlightDB");
            }

            observabilityService.start("findPassengerDB");
            PassengerEntity passenger;
            try {
                passenger = passengerRepository.findById(bookingDTO.getPassengerId())
                        .orElseThrow(() -> new RuntimeException("Passenger not found"));
            } finally {
                observabilityService.stop("findPassengerDB");
            }

            observabilityService.start("checkAvailabilityDB");
            try {
                long bookedSeats = bookingRepository.countByFlightId(flight.getId());
                if (bookedSeats >= flight.getCapacity()) {
                    throw new RuntimeException("No available seats");
                }
            } finally {
                observabilityService.stop("checkAvailabilityDB");
            }

            BookingEntity booking = new BookingEntity();
            booking.setFlight(flight);
            booking.setPassenger(passenger);
            booking.setSeatClass(bookingDTO.getSeatClass());
            booking.setSeatNumber(bookingDTO.getSeatNumber());
            booking.setBookingTime(LocalDateTime.now());

            observabilityService.start("saveBookingDB");
            try {
                booking = bookingRepository.save(booking);
                return BookingMapper.toDTO(booking);
            } finally {
                observabilityService.stop("saveBookingDB");
            }
        } finally {
            observabilityService.stop("createBookingDB");
        }
    }

    public List<BookingDTO> getBookingsByFlight(Long flightId) {
        observabilityService.start("getBookingsByFlightDB");
        try {
            return bookingRepository.findByFlightId(flightId).stream()
                    .map(BookingMapper::toDTO)
                    .collect(Collectors.toList());
        } finally {
            observabilityService.stop("getBookingsByFlightDB");
        }
    }

    public List<BookingDTO> getAllBookings() {
        observabilityService.start("getAllBookingsDB");
        try {
            return bookingRepository.findAll().stream()
                    .map(BookingMapper::toDTO)
                    .collect(Collectors.toList());
        } finally {
            observabilityService.stop("getAllBookingsDB");
        }
    }
}