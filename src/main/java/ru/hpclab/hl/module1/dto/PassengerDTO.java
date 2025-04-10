package ru.hpclab.hl.module1.dto;

public class PassengerDTO {
    private Long id;
    private String fullName;
    private String passportNumber;
    private String contactInfo;

    public PassengerDTO() {
    }

    public PassengerDTO(Long id, String fullName, String passportNumber, String contactInfo) {
        this.id = id;
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.contactInfo = contactInfo;
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

    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}