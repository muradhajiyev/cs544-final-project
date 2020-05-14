package edu.miu.cs544.medappointment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.miu.cs544.medappointment.entity.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("delete from Reservation where appointment.id =:appointmentId")
    void deleteAllByAppointment_Id(@Param("appointmentId") Long appointmentId);

    Page<Reservation> findByAppointmentId(Long appointmentId, Pageable pageable);
}
