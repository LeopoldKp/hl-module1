// Passenger.java
package ru.hpclab.hl.module1.model;

import java.util.UUID;

public class Passenger {
    private UUID id;
    private String fullName;
    private String passportData;
    private String contactInfo;

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassportData() { return passportData; }
    public void setPassportData(String passportData) { this.passportData = passportData; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}