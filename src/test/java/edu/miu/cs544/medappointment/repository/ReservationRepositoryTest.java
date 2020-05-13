package edu.miu.cs544.medappointment.repository;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testCancelReservationByReservationId() {
        //given

        User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");

        Appointment appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setLocation("McLaughlin");
        appointment.setProvider(user);

        User user1 = entityManager.persist(user);
        Appointment appointment1 = entityManager.persist(appointment);
        entityManager.flush();

        Reservation persisted_data = new Reservation();
        persisted_data.setCreatedAt(new Date());
        persisted_data.setUpdatedAt(new Date());
        persisted_data.setStatus(Status.PENDING);
        persisted_data.setAppointment(appointment);
        persisted_data.setConsumer(user);
        entityManager.persist(persisted_data);
        entityManager.flush();
        // checking the status is changed to DECLINED
        // when
        Reservation updateReservation = reservationRepository.findById(persisted_data.getId()).get();
        updateReservation.setStatus(Status.DECLINED);
        reservationRepository.save(updateReservation);
        // then
        assertEquals(Status.DECLINED, updateReservation.getStatus());
    }

}