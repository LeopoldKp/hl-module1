package ru.hpclab.hl.module1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class FlightDTO {
    private Long id;
    private String flightNumber;
    private String departure;  // Обратите внимание на правильное написание
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;  // Изменили с departureTime

    private int capacity;
    private int availableSeats;

    // Геттеры и сеттеры (ВНИМАНИЕ на правильные названия)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDeparture() { return departure; }  // Не getDepartment!
    public void setDeparture(String departure) { this.departure = departure; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getDepartureDate() { return departureDate; }  // Не getDepartmentDate!
    public void setDepartureDate(LocalDate departureDate) { this.departureDate = departureDate; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}