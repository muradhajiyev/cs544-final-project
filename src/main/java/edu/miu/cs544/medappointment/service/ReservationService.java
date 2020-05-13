package edu.miu.cs544.medappointment.service;

import java.util.List;

import edu.miu.cs544.medappointment.shared.ReservationDto;


import edu.miu.cs544.medappointment.entity.Reservation;

public interface ReservationService {
	Reservation createReservation(ReservationDto reservation);
	
	public ReservationDto getReservationbyId(long id) throws Exception;
	
	public List<ReservationDto> getAllReservations();
}
