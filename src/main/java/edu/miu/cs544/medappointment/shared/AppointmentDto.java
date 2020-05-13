package edu.miu.cs544.medappointment.shared;

import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDto {
    private long id;
    private LocalDateTime dateTime;
    private String location;
    private User provider;
    private List<ReservationDto> reservations;


    public AppointmentDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
}
