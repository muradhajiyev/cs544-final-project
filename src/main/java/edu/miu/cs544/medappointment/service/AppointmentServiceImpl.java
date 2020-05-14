package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;

import edu.miu.cs544.medappointment.shared.ReservationDto;
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
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);

        // TODO:: get authenticated User and pass to appointmentDto
        // this is hardcoded, we should get user from Authentication manager.
        User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        userRepository.save(user);

        appointment.setProvider(user);
        Appointment result = appointmentRepository.save(appointment);
        return modelMapper.map(result, AppointmentDto.class);
    }

    @Override
    public Page<AppointmentDto> getAll(Pageable page, Optional<Status> status) {
        if (!status.isPresent())
            return appointmentRepository.findAll(page).map(appointment -> convertToAppointmentDto(appointment));
        else
            return appointmentRepository.findDistinctByReservationsStatus(status.get(), page).map(appointment -> convertToAppointmentDto(appointment));
    }

    @Override
    public AppointmentDto getById(Long id) {
        return convertToAppointmentDto(appointmentRepository.findById(id).get());
    }

    @Override
    public Page<ReservationDto> getAppointmentReservations(long id, Pageable page) {
        Page<Reservation> reservations = reservationRepository.findByAppointmentId(id, page);
        return reservations.map(reservation -> convertToReservationDto(reservation));
    }


    private AppointmentDto convertToAppointmentDto(Appointment appointment) {
        if (appointment == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        return appointmentDto;
    }

    private ReservationDto convertToReservationDto(Reservation reservation) {
        if (reservation == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
        return reservationDto;
    }

    @Override
    public Appointment updateAppointmentById(Long appointmentId, AppointmentDto newAppointment) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Appointment newApp = modelMapper.map(newAppointment, Appointment.class);

        // Appointment ap = createAppointment(appointment);

        Optional<Appointment> oldAppointment = appointmentRepository.findById(appointmentId);

        if (!oldAppointment.isPresent()) {
            throw new Exception("No appointment found");
        }

        Appointment appointment = oldAppointment.get();
        appointment.setDateTime(newApp.getDateTime());
        appointment.setLocation(newApp.getLocation());

        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        reservationRepository.deleteAllByAppointment_Id(appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }

}
