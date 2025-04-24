package ru.hpclab.hl.module1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class FlightAvailabilityResponse {
    private String flightNumber;
    private String departure;
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    private int availableSeats;

    // Конструктор, геттеры и сеттеры
    public FlightAvailabilityResponse() {}

    public FlightAvailabilityResponse(String flightNumber, String departure,
                                      String destination, LocalDate departureDate,
                                      int availableSeats) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.availableSeats = availableSeats;
    }

    // Геттеры и сеттеры
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}