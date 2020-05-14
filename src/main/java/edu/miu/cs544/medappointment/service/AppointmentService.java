package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointment);
    Page<AppointmentDto> getAll(Pageable page, Optional<Status> status);
    Page<AppointmentDto> getAllAvailable(Pageable page);
    AppointmentDto getById(Long id);
    Page<ReservationDto> getAppointmentReservations(long id, Pageable pageable);
    Appointment updateAppointmentById(Long appointmentIs, AppointmentDto appointment) throws Exception;
    void deleteAppointment(Long appointmentId);
}
