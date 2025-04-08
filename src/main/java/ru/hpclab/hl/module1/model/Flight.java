package ru.hpclab.hl.module1.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Flight {
    private UUID flightNumber;
    private String destination;
    private LocalDateTime departureTime;
    private int capacity;
    private int bookedSeats;

    // Геттеры и сеттеры
    public UUID getFlightNumber() { return flightNumber; }
    public void setFlightNumber(UUID flightNumber) { this.flightNumber = flightNumber; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getBookedSeats() { return bookedSeats; }
    public void setBookedSeats(int bookedSeats) { this.bookedSeats = bookedSeats; }
    public int getAvailableSeats() { return capacity - bookedSeats; }
}