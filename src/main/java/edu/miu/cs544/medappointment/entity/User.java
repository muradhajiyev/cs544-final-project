package edu.miu.cs544.medappointment.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String firstName;
	private String lastName;
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(name = "User_Role",
			joinColumns = { @JoinColumn(name = "UserId") },
			inverseJoinColumns = { @JoinColumn(name = "RoleId") })
	private List<Role> roles;

	@OneToMany(mappedBy = "provider")
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "consumer")
	private List<Reservation> reservations;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public User(String firstName, String lastName, String email,
				String username, String password, List<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean addRole(Role role) {
		return roles.add(role);
	}

	public boolean removeOrderLine(Role role) {
		return roles.remove(role);
	}

	public Long getId() {
		return id;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public boolean addAppointment(Appointment appointment) {
		return appointments.add(appointment);
	}

	public boolean removeAppointment(Appointment appointment) {
		return appointments.remove(appointment);
	}

	public boolean addReservation(Reservation reservation) {
		return reservations.add(reservation);
	}

	public boolean removeReservations(Reservation reservation) {
		return reservations.add(reservation);
	}
}
