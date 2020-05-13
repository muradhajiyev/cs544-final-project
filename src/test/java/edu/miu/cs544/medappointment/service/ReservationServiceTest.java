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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationServiceTest {

    private User user;
    private Reservation reservation;
    private Appointment appointment;

    //    @Autowired
    @InjectMocks
    private ReservationService reservationService = new ReservationServiceImpl();

    //    @MockBean
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    // temporary till implement Security
    @Mock
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {

        //mocking
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
    }

    @Test
    void testCancelReservationByReservationId() {
        user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        entityManager.persist(user);
        entityManager.flush();
        appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setLocation("McLaughlin");
        appointment.setProvider(user);
        entityManager.persist(appointment);
        entityManager.flush();

        reservation = new Reservation();
        reservation.setCreatedAt(new Date());
        reservation.setUpdatedAt(new Date());
        reservation.setStatus(Status.PENDING);
        reservation.setAppointment(appointment);
        reservation.setConsumer(user);

        entityManager.persist(reservation);
        entityManager.flush();

        Reservation updated = reservationService.cancelReservation(reservation.getId());

        assertEquals(reservation.getStatus(), updated.getStatus());

    }
}