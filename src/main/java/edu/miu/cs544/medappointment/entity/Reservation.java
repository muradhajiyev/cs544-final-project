package edu.miu.cs544.medappointment.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@CreationTimestamp
    private Date createdAt;

	@UpdateTimestamp
    private Date updatedAt;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; 
    
    @ManyToOne
    @JoinColumn(name="consumer", nullable = false)
    private User consumer;
    
    @ManyToOne
    @JoinColumn(name="appointment_id", nullable = false)
    private Appointment appointment;

	public Reservation() {
	}

	public Reservation(Date createdAt, Date updatedAt, Status status, User consumer, Appointment appointment) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.consumer = consumer;
		this.appointment = appointment;
	}

	public Reservation(Date createdAt, Date updatedAt, Status status) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
	}

	public Reservation(Status status, User consumer, Appointment appointment) {
		createdAt= new Date();
		updatedAt=new Date();
		this.status = status;
		this.consumer = consumer;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id){ this.id = id; }
    
}
