package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {
        ModelMapper modelMapper=new ModelMapper();
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
    public Page<AppointmentDto> getAll(Pageable page, Optional<Status> status) {
        //first get list from the page result, then do mapping and return convert the mapped list to page
//        Page<AppointmentDto> entities = appointmentRepository.findAll(page).map(appointment -> convertToAppointmentDto(appointment));
        if(!status.isPresent())
            return appointmentRepository.findAll(page).map(appointment -> convertToAppointmentDto(appointment));
        else
            return appointmentRepository.findDistinctByReservationsStatus(status.get(),page).map(appointment -> convertToAppointmentDto(appointment));
    }

    @Override
    public AppointmentDto getById(Long id) {
        return convertToAppointmentDto(appointmentRepository.findById(id).get());
    }


    private AppointmentDto convertToAppointmentDto(Appointment appointment) {
        if(appointment!=null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
            return appointmentDto;
        }else{
            return null;
        }
    }

}
