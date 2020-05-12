package edu.miu.cs544.medappointment.ui.model;

import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.User;

import java.time.LocalDateTime;

public class AppointmentResponseModel {

    private Long id;
    private LocalDateTime dateTime;
    private String location;
    private UserResponseModel provider;

    public AppointmentResponseModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserResponseModel getProvider() {
        return provider;
    }

    public void setProvider(UserResponseModel provider) {
        this.provider = provider;
    }
}
