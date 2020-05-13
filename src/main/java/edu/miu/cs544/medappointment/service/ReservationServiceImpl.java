package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
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

    /*@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ReservationRepository reservationRepository;*/

	@Override
	public ReservationDto getReservationbyId(long id) throws Exception 
	{
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Reservation reservation = reservationRepository.findById(id).orElseThrow(Exception::new);
		return modelMapper.map(reservation, ReservationDto.class);
	}

	@Override
	public List<ReservationDto> getAllReservations() 
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<Reservation> reservations = reservationRepository.findAll();
		if (reservations != null)
			return reservations.stream().map(entity -> modelMapper.map(entity, ReservationDto.class))
					.collect(Collectors.toList());
		else
			return null;
	}

}
