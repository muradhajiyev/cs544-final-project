package edu.miu.cs544.medappointment.repository;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private List<User> users;
    private List<Appointment> appointments;
    private Reservation reservation;

    @BeforeEach
    public void setUp(){
        //given
        users = new ArrayList();
        users.add(new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456"));
        users.add(new User("TM Checker2", "TM Checker2", "checker2@gmail.com", "checker2", "1234567"));
        users.add(new User("TM Checker3", "TM Checker3", "checker3@gmail.com", "checker3", "12345678"));
        users.add(new User("TM Checker4", "TM Checker4", "checker4@gmail.com", "checker4", "123456789"));
        appointments=new ArrayList<>();
        appointments.add(new Appointment(LocalDateTime.now(),"Verill Hall #35", users.get(0)));
        appointments.add(new Appointment(LocalDateTime.now(),"Veril hall", users.get(1)));
        appointments.add(new Appointment(LocalDateTime.now(),"library", users.get(2)));
        appointments.add(new Appointment(LocalDateTime.now(),"Dalby hall", users.get(3)));
        reservation=new Reservation(Status.PENDING,users.get(0),appointments.get(0));
        for(User user:users){
            entityManager.persist(user);
        }
        for(Appointment appointment:appointments){
            entityManager.persist(appointment);
        }
        entityManager.persist(reservation);
        entityManager.flush();
    }


    @Test
    public void whenFindAllById_ValidId_thenReturnAppointment() throws Exception{
        // when
        Optional<Appointment> found = appointmentRepository.findById(appointments.get(0).getId());

        // then
        assertEquals(appointments.get(0).getLocation(), found.get().getLocation());
        assertEquals(appointments.get(0).getDateTime(), found.get().getDateTime());
        assertEquals(appointments.get(0).getProvider(), found.get().getProvider());
        assertEquals(appointments.get(0).getReservation(), found.get().getReservation());

    }

    @Test
    public void findAppointmentReservationsStatus_Status_theReturnAppointmentList() throws Exception{
        // when
        Pageable page = PageRequest.of(0,20);
        Page<Appointment> found = appointmentRepository.findDistinctByReservationsStatus(Status.PENDING, page);
        assertEquals(appointments.get(0).getLocation(),found.getContent().get(0).getLocation());
    }
}