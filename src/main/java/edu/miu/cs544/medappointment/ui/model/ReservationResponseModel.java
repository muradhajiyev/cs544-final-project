package edu.miu.cs544.medappointment.ui.model;

import java.util.Date;

import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.shared.AppointmentDto;

public class ReservationResponseModel {

    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private UserResponseModel consumer;
    private AppointmentResponseModel appointment;

    public ReservationResponseModel() {
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

    public UserResponseModel getConsumer() {
        return consumer;
    }

    public void setConsumer(UserResponseModel consumer) {
        this.consumer = consumer;
    }

    public AppointmentResponseModel getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentResponseModel appointment) {
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }
}
