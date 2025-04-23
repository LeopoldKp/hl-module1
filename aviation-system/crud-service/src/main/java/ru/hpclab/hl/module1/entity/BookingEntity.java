package ru.hpclab.hl.module1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private PassengerEntity passenger;

    @Column(name = "seat_class", nullable = false)
    private String seatClass;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    public BookingEntity() {
    }

    public BookingEntity(Long id, FlightEntity flight, PassengerEntity passenger,
                         String seatClass, String seatNumber, LocalDateTime bookingTime) {
        this.id = id;
        this.flight = flight;
        this.passenger = passenger;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public PassengerEntity getPassenger() {
        return passenger;
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

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
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
        return "BookingEntity{" +
                "id=" + id +
                ", flight=" + flight +
                ", passenger=" + passenger +
                ", seatClass='" + seatClass + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", bookingTime=" + bookingTime +
                '}';
    }
}