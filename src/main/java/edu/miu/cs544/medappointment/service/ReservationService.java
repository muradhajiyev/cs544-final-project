package edu.miu.cs544.medappointment.service;

import java.util.List;

import edu.miu.cs544.medappointment.shared.ReservationDto;


import edu.miu.cs544.medappointment.entity.Reservation;

public interface ReservationService {
	ReservationDto createReservation(ReservationDto reservation) throws Exception;
	ReservationDto getReservationById(Long id);
	ReservationDto changeStatus(ReservationDto reservationDto, Long id) throws Exception;
	ReservationDto convertToReservationDto(Reservation reservation);
	List<ReservationDto> viewUserReservations();
	List<ReservationDto> convertToListReservationDto(List<Reservation> resList);
}
