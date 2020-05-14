package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(AppointmentDto appointment);
    Page<AppointmentDto> getAll(Pageable page, Optional<Status> status);
    AppointmentDto getById(Long id);
    Appointment updateAppointmentById(Long appointmentIs, AppointmentDto appointment) throws Exception;
    void deleteAppointment(Long appointmentId);
}
