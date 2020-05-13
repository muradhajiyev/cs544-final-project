package edu.miu.cs544.medappointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.shared.ReservationDto;

@ExtendWith(SpringExtension.class)
public class ReservationServiceTest 
{
	private User user;
	private Reservation reservation;
	private List<Reservation> reservations;
	private Appointment appointment;
	
    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();
    
    @Mock
    private ReservationRepository reservationRepository;
    
	@BeforeEach
	void setUp() 
	{
		// given
		reservations = new ArrayList<Reservation>();
		user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
		appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setLocation("McLaughlin");
        appointment.setProvider(user);
		reservation = new Reservation();
		reservation.setAppointment(appointment);
		reservation.setConsumer(user);
		reservation.setCreatedAt(new Date());
		reservation.setStatus(Status.PENDING);
		reservations.add(reservation);
		reservations.add(reservation);
		// mocking
		when(reservationRepository.findById(new Long(1))).thenReturn(Optional.of(reservation));
		when(reservationRepository.findAll()).thenReturn(reservations);
	}

	@Test
	void getReservationByID_reservationId_returnReservation() throws Exception 
	{
		ReservationDto returnedReservation = reservationService.getReservationbyId(1);
		assertEquals(returnedReservation.getAppointment().getLocation(), "McLaughlin");
		assertEquals(returnedReservation.getStatus(), Status.PENDING);
	}
	
	
	@Test
	void getgetAllReservations_void_returnReservationList() throws Exception 
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<ReservationDto> returnedReservations = reservationService.getAllReservations();
		assertEquals(returnedReservations.size(), 2);
		assertEquals(returnedReservations.get(0).getStatus(), Status.PENDING);
		assertEquals(returnedReservations.get(1).getAppointment().getLocation(), "McLaughlin");
	}
}
