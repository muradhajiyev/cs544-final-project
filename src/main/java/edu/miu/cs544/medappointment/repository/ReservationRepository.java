package edu.miu.cs544.medappointment.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.medappointment.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
}
=======
import edu.miu.cs544.medappointment.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
>>>>>>> 8573f70673d13988d49dab2c0bf64c3aabfbefe8
