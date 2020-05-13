package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);

        // TODO:: get authenticated User and pass to appointmentDto
        // this is hardcoded, we should get user from Authentication manager.
        User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        userRepository.save(user);
        appointment.setProvider(user);
        Appointment result = appointmentRepository.save(appointment);
        return result;
    }

    @Override
    public void deleteAppointment(Long appointmentId){
//        reservationRepository.deleteAllByAppointment_Id(appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

}
