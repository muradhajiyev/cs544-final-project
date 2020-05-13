package edu.miu.cs544.medappointment.service;

<<<<<<< HEAD
import java.util.List;

import edu.miu.cs544.medappointment.shared.ReservationDto;

public interface ReservationService 
{
	public ReservationDto getReservationbyId(long id) throws Exception;
	
	public List<ReservationDto> getAllReservations();
=======

import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.shared.ReservationDto;

public interface ReservationService {
	Reservation createReservation(ReservationDto reservation);
	
	public ReservationDto getReservationbyId(long id) throws Exception;
	
	public List<ReservationDto> getAllReservations();
>>>>>>> 8573f70673d13988d49dab2c0bf64c3aabfbefe8
}
