package edu.miu.cs544.medappointment.ui.model;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReservationRequestModel {

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private User consumer;
    private Appointment appointment;

    public ReservationRequestModel() {
    }
    public ReservationRequestModel(Status status){
        this.status = status;
    }
    public ReservationRequestModel(Status status, Appointment appointment){
        this.status = status;
        this.appointment = appointment;
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

}
