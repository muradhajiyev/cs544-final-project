package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Reservation createReservation(ReservationDto reservationDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);

        // TODO:: get authenticated User and pass to appointmentDto
        // this is hardcoded, we should get user from Authentication manager.
        User userStudent = userRepository.getOne(3L);
        reservation.setConsumer(userStudent);

        Appointment appointment = appointmentRepository.getOne(1L);
        //reservation.setAppointment(appointment);

        Reservation result = reservationRepository.save(reservation);
        return result;
    }

    protected Appointment testAppointmentData(){
        User userChecker = userRepository.getOne(2L);
        //Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35",userChecker);
        //return appointmentRepository.save(appointment);
        Appointment appointment = appointmentRepository.getOne(1L);
        return appointment;
    }

    @Override
    public Reservation cancelReservation(Long id){
        Optional<Reservation> oldReservation = reservationRepository.findById(id);
        Reservation reservation = oldReservation.get();
        reservation.setStatus(Status.DECLINED);
        return reservationRepository.save(reservation);
    }

}
