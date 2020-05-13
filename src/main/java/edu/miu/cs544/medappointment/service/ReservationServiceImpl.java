package edu.miu.cs544.medappointment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.shared.ReservationDto;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService 
{

	/*@Autowired
	private ModelMapper modelMapper;*/
	@Autowired
	private ReservationRepository reservationRepository;

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
