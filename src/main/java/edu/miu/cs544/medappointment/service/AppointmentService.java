package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(AppointmentDto appointment);
    void deleteAppointment(Long appointmentId);
    List<AppointmentDto> getAll();
    Page<AppointmentDto> getAll(Pageable page);
    AppointmentDto getById(Long id);
    Long getAllCount();
}
