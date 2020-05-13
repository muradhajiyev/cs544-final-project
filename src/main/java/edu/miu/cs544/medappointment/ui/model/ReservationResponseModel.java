package edu.miu.cs544.medappointment.ui.model;

<<<<<<< HEAD
import java.util.Date;

import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.shared.AppointmentDto;

public class ReservationResponseModel 
{
	  private long id;
	    private Date createdAt;
	    private Date updatedAt;
	   //TODO: change to DTOs
	    private Status status; 
	    private User consumer;
	    private AppointmentDto appointment;

		public ReservationResponseModel() {
		}

		public ReservationResponseModel(Date createdAt, Date updatedAt, Status status, User consumer, AppointmentDto appointment) {
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.status = status;
			this.consumer = consumer;
			this.appointment = appointment;
		}

		public ReservationResponseModel(Date createdAt, Date updatedAt, Status status) {
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.status = status;
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

		public AppointmentDto getAppointment() {
			return appointment;
		}

		public void setAppointment(AppointmentDto appointment) {
			this.appointment = appointment;
		}

		public Long getId() {
			return id;
		}
=======


import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;

import java.util.Date;

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
>>>>>>> 8573f70673d13988d49dab2c0bf64c3aabfbefe8
}
