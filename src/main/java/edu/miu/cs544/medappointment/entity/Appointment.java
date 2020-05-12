package edu.miu.cs544.medappointment.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String location;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="provider", nullable = false)
    private User provider;
    
    @OneToMany(mappedBy = "appointment")
	private List<Reservation> reservations;

    public Appointment(){ }

    public Appointment(LocalDateTime dateTime, String location) {
        this.dateTime = dateTime;
        this.location = location;
    }

    public Appointment(LocalDateTime dateTime, String location, User provider){
        this.dateTime = dateTime;
        this.location = location;
        this.provider = provider;
    }

    public Long getId() {
        return id;
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

	public List<Reservation> getReservation() {
		return reservations;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservations = reservation;
	}
	
	public boolean addReservation(Reservation reservation) {
		return reservations.add(reservation);
	}
	
	public boolean removeOrderLine(Reservation reservation) {
		return reservations.remove(reservation);
	}
	
}
