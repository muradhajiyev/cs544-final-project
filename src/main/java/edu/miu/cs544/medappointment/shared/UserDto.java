package edu.miu.cs544.medappointment.shared;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private List<RoleDto> roles;
    private List<AppointmentDto> appointments;
    private List<ReservationDto> reservations;

    public UserDto() {
        super();
        // TODO Auto-generated constructor stub
    }


    public UserDto(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UserDto(String firstName, String lastName, String email,
                String username, String password, List<RoleDto> roles) {
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public boolean addRole(RoleDto role) {
        return roles.add(role);
    }

    public boolean removeRole(RoleDto role) {
        return roles.remove(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){ this.id = id; }

    public List<AppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

    public boolean addAppointment(AppointmentDto appointment) {
        return appointments.add(appointment);
    }

    public boolean removeAppointment(AppointmentDto appointment) {
        return appointments.remove(appointment);
    }

    public boolean addReservation(ReservationDto reservation) {
        return reservations.add(reservation);
    }

    public boolean removeReservations(ReservationDto reservation) {
        return reservations.add(reservation);
    }
}
