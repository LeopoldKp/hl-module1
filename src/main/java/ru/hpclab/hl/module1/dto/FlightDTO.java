package ru.hpclab.hl.module1.dto;

import java.time.LocalDateTime;

public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departure;
    private String destination;
    private LocalDateTime departureTime;
    private int capacity;
    private int availableSeats;

    public FlightDTO() {
    }

    public FlightDTO(Long id, String flightNumber, String departure, String destination,
                     LocalDateTime departureTime, int capacity, int availableSeats) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.capacity = capacity;
        this.availableSeats = availableSeats;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
                ", departureTime=" + departureTime +
                ", capacity=" + capacity +
                ", availableSeats=" + availableSeats +
                '}';
    }
}