package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.ui.model.ReservationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReservationServiceTest {

    private User checker;
    private Appointment appointment;
    private User student;
    private Reservation reservation;

    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        checker = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        student = new User("Student fname", "Student lname", "student@miu.edu", "student","123456");

        appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35", checker);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(Status.PENDING);
        reservation.setConsumer(student);
        reservation.setAppointment(appointment);

        //mocking
        when(userRepository.save(any(User.class))).thenReturn(student);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(reservationRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
    }
    @Test
    void createReservation_ReservationEntity_ThenReturnSavedReservation() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        appointmentDto.setId(1L);
        reservationDto.setAppointmentDto(appointmentDto);
        System.out.println("RESEVATION DTO APP:::::::::");
        System.out.println(appointmentDto.getId());

        ReservationDto createdDto = reservationService.createReservation(reservationDto);



        //Reservation created = modelMapper.map(createdDto, Reservation.class);

        assertEquals(student.getEmail(), createdDto.getConsumer().getEmail());
        assertEquals(appointment.getDateTime(), createdDto.getAppointmentDto().getDateTime());
        assertEquals(appointment.getLocation(), createdDto.getAppointmentDto().getLocation());
        assertEquals(checker.getEmail(), createdDto.getAppointmentDto().getProvider().getEmail());
        assertEquals(reservation.getStatus(), createdDto.getStatus());
    }


    @Test
    void testCancelReservationByReservationId() throws Exception {
        ReservationDto cancelReservation = reservationService.cancelReservation(1L);
        assertEquals(Status.CANCELED, cancelReservation.getStatus());
    }
    @Test
    void changeReservationStatus_ValidIdStatus_ThenReturnChangedReservation() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //reservation.setId(1L);
        reservation.setStatus(Status.CANCELED);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        appointmentDto.setId(1L);
        ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
        reservationDto.setAppointmentDto(appointmentDto);
        ReservationDto changedDto = reservationService.changeStatus(reservationDto, 1L);
        Reservation changed = modelMapper.map(changedDto, Reservation.class);

        assertEquals(Status.CANCELED, changed.getStatus());
    }
    

}