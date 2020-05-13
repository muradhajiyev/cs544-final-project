package edu.miu.cs544.medappointment.repository;

<<<<<<< HEAD
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
=======
import edu.miu.cs544.medappointment.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> 8573f70673d13988d49dab2c0bf64c3aabfbefe8
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
<<<<<<< HEAD

=======
>>>>>>> 8573f70673d13988d49dab2c0bf64c3aabfbefe8
}
