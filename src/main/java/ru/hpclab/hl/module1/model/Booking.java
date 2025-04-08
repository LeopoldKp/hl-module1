package ru.hpclab.hl.module1.model;

import java.util.UUID;

public class Booking {
    private UUID id;
    private UUID flightNumber;
    private UUID passengerId;
    private String serviceClass;
    private String seatNumber;

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getFlightNumber() { return flightNumber; }
    public void setFlightNumber(UUID flightNumber) { this.flightNumber = flightNumber; }
    public UUID getPassengerId() { return passengerId; }
    public void setPassengerId(UUID passengerId) { this.passengerId = passengerId; }
    public String getServiceClass() { return serviceClass; }
    public void setServiceClass(String serviceClass) { this.serviceClass = serviceClass; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
}