package edu.miu.cs544.medappointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Date;
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
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;

@ExtendWith(SpringExtension.class)
public class ReservationServiceTest 
{
	private User user;
	private Reservation reservation;
	private Appointment appointment;
	
    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();
    
    @Mock
    private ReservationRepository reservationRepository;
    
	@BeforeEach
	void setUp() 
	{
		// given
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

		// mocking
		when(reservationRepository.findById(new Long(1))).thenReturn(reservation);
	}

	@Test
	void getReservationByID_reservationId_returnReservation() throws Exception 
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto returnedReservation = reservationService.getReservationbyId(1);

		assertEquals(returnedReservation.getAppointment().getLocation(), "McLaughlin");
		assertEquals(returnedReservation.getStatus(), Status.PENDING);
	}
}
