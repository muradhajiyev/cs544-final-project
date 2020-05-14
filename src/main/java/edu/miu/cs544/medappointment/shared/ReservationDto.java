package edu.miu.cs544.medappointment.shared;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;

import java.io.Serializable;
import java.util.Date;

public class ReservationDto implements Serializable {
    private long id;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private UserDto consumer;
    private AppointmentDto appointment;

    public ReservationDto() {
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserDto getConsumer() {
        return consumer;
    }

    public void setConsumer(UserDto consumer) {
        this.consumer = consumer;
    }

    public AppointmentDto getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDto appointment) {
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){ this.id = id; }
}
