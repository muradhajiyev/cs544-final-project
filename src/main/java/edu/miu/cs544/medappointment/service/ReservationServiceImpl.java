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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        Appointment appointment = appointmentRepository.findById(reservationDto.getAppointmentDto().getId()).orElse(null);
        if(appointment==null) throw new Exception("Appointment not found");
        reservation.setAppointment(appointment);

        // TODO:: get authenticated User and pass to appointmentDto
        // this is hardcoded, we should get user from Authentication manager.
        User userStudent = userService.getAuthUser();
        if(userStudent==null) throw new Exception("User not found!");
        reservation.setConsumer(userStudent);

        Reservation result = reservationRepository.save(reservation);
        return convertToReservationDto(result);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return convertToReservationDto(reservationRepository.getOne(id));
    }

    @Override
    public ReservationDto changeStatus(ReservationDto reservationDto, Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        if(reservationRepository.findById(id)==null) throw new Exception("Reservation not found!");
        System.out.println(id);
        System.out.println(reservationDto.getAppointmentDto());
        reservation.setId(id);
        System.out.println("RESERVATION::::::::::::");
        System.out.println(reservationDto.getAppointmentDto().getId());
        System.out.println(reservation.getId() + ": " + reservation.getStatus());

        Appointment appointment = appointmentRepository.findById(reservationDto.getAppointmentDto().getId()).orElse(null);
        if(appointment==null) throw new Exception("Appointment not found!");
        reservation.setAppointment(appointment);

        User consumer = userRepository.getOne(3L);
        if(consumer==null) throw new Exception("Consumer not found!");
        reservation.setConsumer(consumer);


        if(reservation!=null){
            Reservation updated = reservationRepository.save(reservation);
            return convertToReservationDto(updated);
        }
        return null;
    }

    @Override
    public ReservationDto convertToReservationDto(Reservation reservation) {
        if(reservation!=null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            ReservationDto reservationDto = modelMapper.map(reservation, ReservationDto.class);
            return reservationDto;
        }else{
            return null;
        }
    }

    protected Appointment testAppointmentData(){
        User userChecker = userRepository.getOne(2L);
        //Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35",userChecker);
        //return appointmentRepository.save(appointment);
        Appointment appointment = appointmentRepository.getOne(1L);
        return appointment;
    }

}
