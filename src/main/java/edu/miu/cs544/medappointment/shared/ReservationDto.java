package edu.miu.cs544.medappointment.shared;

import java.util.Date;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.entity.Appointment;


public class ReservationDto {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private Status status;
    private User consumer;
    private Appointment appointment;

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

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Long getId() {
        return id;
    }
}
