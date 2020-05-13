package edu.miu.cs544.medappointment.service;

import java.util.List;

import edu.miu.cs544.medappointment.shared.ReservationDto;

public interface ReservationService 
{
	public ReservationDto getReservationbyId(long id) throws Exception;
	
	public List<ReservationDto> getAllReservations();
}
