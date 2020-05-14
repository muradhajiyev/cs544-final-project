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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    //private Long consumerId;
    private Long appointmentId;

    public ReservationRequestModel() {
    }
    public ReservationRequestModel(Status status){
        this.status = status;
    }
    public ReservationRequestModel(Status status, Long appointmentId){
        this.status = status;
        this.appointmentId = appointmentId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }*/

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

}
