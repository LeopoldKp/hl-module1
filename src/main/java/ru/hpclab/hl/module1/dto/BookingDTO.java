package ru.hpclab.hl.module1.dto;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long id;
    private Long flightId;
    private Long passengerId;
    private String seatClass;
    private String seatNumber;
    private LocalDateTime bookingTime;

    public BookingDTO() {
    }

    public BookingDTO(Long id, Long flightId, Long passengerId,
                      String seatClass, String seatNumber, LocalDateTime bookingTime) {
        this.id = id;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", passengerId=" + passengerId +
                ", seatClass='" + seatClass + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", bookingTime=" + bookingTime +
                '}';
    }
}