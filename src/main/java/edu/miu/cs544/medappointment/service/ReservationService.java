package edu.miu.cs544.medappointment.service;

import java.util.List;

import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.shared.ReservationDto;


import edu.miu.cs544.medappointment.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {
    ReservationDto cancelReservation(Long id) throws Exception;
	public ReservationDto getReservationbyId(long id) throws Exception;
	public List<ReservationDto> getAllReservations();
	ReservationDto createReservation(ReservationDto reservation) throws Exception;
	ReservationDto getReservationById(Long id);
	ReservationDto changeReservationStatus(ReservationDto reservationDto, Long id) throws Exception;
	Page<ReservationDto> getUserReservations(Pageable pageable);
	ReservationDto getReservation(Long id) throws Exception;
	boolean checkHasAcceptedReservations(Long appointmentId);
}
