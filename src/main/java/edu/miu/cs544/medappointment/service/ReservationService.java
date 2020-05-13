package edu.miu.cs544.medappointment.service;



import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.shared.ReservationDto;

public interface ReservationService {
    Reservation createReservation(ReservationDto reservation);
    Reservation cancelReservation(Long id);
}
