package ru.hpclab.hl.module1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departure;
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    private int capacity;

    @JsonIgnore
    private int availableSeats;

    public FlightDTO() {
    }

    public FlightDTO(Long id, String flightNumber, String departure, String destination,
                     LocalDate departureDate, int capacity, int availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", capacity=" + capacity +
                ", availableSeats=" + availableSeats +
                '}';
    }
}