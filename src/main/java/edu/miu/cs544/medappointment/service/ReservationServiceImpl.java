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

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        if(reservationRepository.findById(id) == null) throw new Exception("Reservation not found!");
        reservation.setId(id);
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

    @Override
	public List<ReservationDto> viewUserReservations()
    {
        User userStudent = userService.getAuthUser();
		List<ReservationDto> r = convertToListReservationDto(reservationRepository.findAll());
		List<ReservationDto> ret = new ArrayList<>();
		for(int i = 0; i < r.size(); i++)
			if(r.get(i).getConsumer().getId() == userStudent.getId())
				ret.add(r.get(i));
		return ret;
	}
    
    @Override
	public List<ReservationDto> convertToListReservationDto(List<Reservation> resList)
	{
		if(null == resList)
			return null;
		else
		{
			ModelMapper modelMapper = new ModelMapper();
			return resList.stream()
					.map(entity -> modelMapper.map(entity, ReservationDto.class))
					.collect(Collectors.toList());
		}
		
	}
    
    protected Appointment testAppointmentData(){
        User userChecker = userRepository.getOne(2L);
        //Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35",userChecker);
        //return appointmentRepository.save(appointment);
        Appointment appointment = appointmentRepository.getOne(1L);
        return appointment;
    }

    @Override
    public ReservationDto cancelReservation(Long id) throws Exception {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Optional<Reservation> currentReservation = reservationRepository.findById(id);
        if (!currentReservation.isPresent())
            throw new Exception("The Reservation not found");

        Reservation reservation = currentReservation.get();
        reservation.setStatus(Status.CANCELED);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapper.map(updatedReservation, ReservationDto.class);
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
