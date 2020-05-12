package edu.miu.cs544.medappointment.ui.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentRequestModel {

    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private String location;

    public AppointmentRequestModel() {
    }

    public AppointmentRequestModel(@NotNull LocalDateTime dateTime, @NotNull String location) {
        this.dateTime = dateTime;
        this.location = location;
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

}
