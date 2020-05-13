package edu.miu.cs544.medappointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.medappointment.entity.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    void deleteAllByAppointment_Id(Long appointment_id);
}
