package edu.miu.cs544.medappointment.repository;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
<<<<<<< HEAD
=======

    Page<Appointment> findDistinctByReservationsStatus(Status status, Pageable page);

    @Query("SELECT DISTINCT a FROM Appointment a LEFT OUTER JOIN a.reservations r WHERE r.status != 'ACCEPTED' OR r.id=null")
    Page<Appointment> findAll(Pageable page);

>>>>>>> f6859f8267101d463c56ab66e0ea072873171304
}
