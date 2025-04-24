package ru.hpclab.hl.module1.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_passenger")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "passport_number", nullable = false, unique = true)
    private String passportNumber;

    @Column(name = "contact_info")
    private String contactInfo;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingEntity> bookings;

    public PassengerEntity() {
    }

    public PassengerEntity(Long id, String fullName, String passportNumber,
                           String contactInfo, List<BookingEntity> bookings) {
        this.id = id;
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.contactInfo = contactInfo;
        this.bookings = bookings;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "PassengerEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}