package edu.miu.cs544.medappointment.entity;

import com.sun.tools.javac.jvm.Gen;
import jdk.vm.ci.meta.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    private String location;

    public Appointment(){ }

    public Appointment(LocalDateTime dateTime, String location){
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
