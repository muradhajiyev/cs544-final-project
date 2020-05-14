package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.*;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setStatus(Status.PENDING);

        Appointment appointment = appointmentRepository.findById(reservationDto.getAppointment().getId()).orElse(null);
        if(appointment==null) throw new Exception("Appointment not found");
        reservation.setAppointment(appointment);

        // Getting user from Authentication manager.
        User userStudent = userService.getAuthUser();
        if(userStudent==null) throw new Exception("User not found!");
        reservation.setConsumer(userStudent);

        Reservation result = reservationRepository.save(reservation);
        ReservationDto createdDto = convertToReservationDto(result);
        UserDto consumerDto = modelMapper.map(userStudent, UserDto.class);
        createdDto.setConsumer(consumerDto);
        return createdDto;
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return convertToReservationDto(reservationRepository.getOne(id));
    }

    @Override
    public ReservationDto changeStatus(ReservationDto reservationDto, Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if(reservation==null) throw new Exception("Reservation not found!");
        reservation.setStatus(reservationDto.getStatus());

        User currentUser = userService.getAuthUser();
        Set<Role> userRoles = currentUser.getRoles();
        String userRole = "";
        for(Role role: userRoles){
            userRole = role.getName();
        }

        if(userRole.equals("STUDENT") && currentUser.getId() != reservation.getConsumer().getId())
            throw new Exception("Access Denied!");

        if(userRole.equals("STUDENT") && !reservation.getStatus().equals(Status.CANCELED))
            throw new Exception("Student can not change reservation status to '"+ reservation.getStatus() + "'!" );

        if(userRole.equals("CHECKER") && reservation.getStatus().equals(Status.CANCELED))
            throw new Exception("TM Checker can not CANCEL reservation!");

        if(reservation==null) throw new Exception("Reservation not found!");

        Reservation updated = reservationRepository.save(reservation);
        ReservationDto updateDto = convertToReservationDto(updated);
        AppointmentDto appointmentDto = modelMapper.map(appointmentRepository.getOne(updated.getAppointment().getId()), AppointmentDto.class);
        updateDto.setAppointment(appointmentDto);
        return updateDto;
    }

    @Override
    public ReservationDto convertToReservationDto(Reservation reservation) {
        if (reservation != null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
            return reservationDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ReservationDto> viewUserReservations() {
        User userStudent = userService.getAuthUser();
        List<ReservationDto> r = convertToListReservationDto(reservationRepository.findAll());
        List<ReservationDto> ret = new ArrayList<>();
        for (int i = 0; i < r.size(); i++)
            if (r.get(i).getConsumer().getId() == userStudent.getId())
                ret.add(r.get(i));
        return ret;
    }

    @Override
    public List<ReservationDto> convertToListReservationDto(List<Reservation> resList) {
        if (resList == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        return resList.stream()
                .map(entity -> modelMapper.map(entity, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto cancelReservation(Long id) throws Exception {
        Optional<Reservation> currentReservation = reservationRepository.findById(id);
        if (!currentReservation.isPresent())
            throw new Exception("The Reservation not found");

        Reservation reservation = currentReservation.get();
        reservation.setStatus(Status.CANCELED);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapper.map(updatedReservation, ReservationDto.class);
    }

    @Override
    public ReservationDto getReservationbyId(long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(Exception::new);
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Reservation> reservations = reservationRepository.findAll();

        if (reservations == null)
            return null;

        return reservations.stream().map(entity -> modelMapper.map(entity, ReservationDto.class))
                .collect(Collectors.toList());
    }

}
