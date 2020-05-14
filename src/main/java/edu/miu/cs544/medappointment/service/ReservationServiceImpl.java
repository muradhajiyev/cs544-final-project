package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.*;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.EmailDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
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
    private JmsTemplate jmsTemplate;
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
        if(checkHasAcceptedReservations(appointment.getId())) throw new Exception("This appointment is not available");
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
    public ReservationDto changeReservationStatus(ReservationDto reservationDto, Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if(reservation==null) throw new Exception("Reservation not found!");
        reservation.setStatus(reservationDto.getStatus());

        User currentUser = userService.getAuthUser();
        List<String> roles = currentUser.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        if(roles.contains("STUDENT") && currentUser.getId() != reservation.getConsumer().getId())
            throw new Exception("Access Denied!");

        if(roles.contains("STUDENT") && !reservation.getStatus().equals(Status.CANCELED))
            throw new Exception("Student can not change reservation status to '"+ reservation.getStatus() + "'!" );

        if(roles.contains("CHECKER") && reservation.getStatus().equals(Status.CANCELED))
            throw new Exception("TM Checker can not CANCEL reservation!");

        if(reservation==null) throw new Exception("Reservation not found!");

        Reservation updated = reservationRepository.save(reservation);

        if (updated.getStatus() == Status.ACCEPTED || updated.getStatus() == Status.DECLINED){
            String checkerEmail = reservationDto.getAppointment().getProvider().getEmail();
            String studentEmail = currentUser.getEmail();
            String message = String.format("Reservation Number #%d from the student - %s has been %s", reservation.getId(), studentEmail, updated.getStatus());
            EmailDto checkerEmailDto = new EmailDto(checkerEmail, "Reservation Status Change", message);
            EmailDto studentEmailDto = new EmailDto(studentEmail, "Reservation Status Change", message);
            jmsTemplate.convertAndSend("MailNotificationQueue", checkerEmailDto);
            jmsTemplate.convertAndSend("MailNotificationQueue", studentEmailDto);
        }

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
        /*ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);*/

        User userStudent = userService.getAuthUser();
        List<ReservationDto> r = convertToListReservationDto(reservationRepository.findAll());
        List<ReservationDto> ret = new ArrayList<>();
        for (int i = 0; i < r.size(); i++) {
            Reservation res = reservationRepository.findById(r.get(i).getId()).orElse(null);
            if (res!=null && res.getConsumer().getId() == userStudent.getId()) {
                /*r.get(i).setConsumerDto(modelMapper.map(res.getConsumer(), UserDto.class));
                r.get(i).setAppointmentDto(modelMapper.map(res.getAppointment(), AppointmentDto.class));*/
                ret.add(r.get(i));
            }
        }
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

    @Override
    public ReservationDto getReservation(Long id) throws Exception {
        User currentUser = userService.getAuthUser();
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if(reservation==null) throw new Exception("Reservation not found!");

        if(reservation.getConsumer().getId()!=currentUser.getId())
            throw new Exception("Access Denied!");

        return convertToReservationDto(reservation);
    }

    @Override
    public boolean checkHasAcceptedReservations(Long appointmentId) {
        List<Reservation> reservations = reservationRepository.findAllByAppointmentId(appointmentId);
        return reservations.size()>0 ? true : false;
    }

}
