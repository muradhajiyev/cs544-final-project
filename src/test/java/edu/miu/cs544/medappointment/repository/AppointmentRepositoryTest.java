package edu.miu.cs544.medappointment.repository;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void whenFindAllById_ValidId_thenReturnAppointment(){
        //given
        User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");

        Appointment appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setLocation("McLaughlin");
        appointment.setProvider(user);

        entityManager.persist(user);
        entityManager.persist(appointment);
        entityManager.flush();

        // when
        Optional<Appointment> found = appointmentRepository.findById(appointment.getId());

        // then
        assertEquals(user.getEmail(), found.get().getProvider().getEmail());
        assertEquals(appointment.getLocation(), found.get().getLocation());
    }

}