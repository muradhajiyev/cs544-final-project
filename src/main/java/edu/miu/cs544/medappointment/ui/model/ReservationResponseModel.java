package edu.miu.cs544.medappointment.ui.model;

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
}
