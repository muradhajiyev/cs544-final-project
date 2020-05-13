package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.shared.AppointmentDto;

public interface AppointmentService {
    Appointment createAppointment(AppointmentDto appointment);
}
