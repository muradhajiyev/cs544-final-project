package edu.miu.cs544.medappointment.ui.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.service.ReservationService;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import edu.miu.cs544.medappointment.ui.model.ReservationResponseModel;

@RestController
@RequestMapping("/v1/reservation")
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/{id}")
	public ResponseEntity<ReservationResponseModel> getReservationById(@PathVariable long id) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservation;
		ReservationResponseModel response;
		try 
		{
			reservation = reservationService.getReservationbyId(id);
			response = mapper.map(reservation, ReservationResponseModel.class);
		}
		catch(Exception e)
		{//No reservation with given id
			response = null;
			e.printStackTrace();
		}
		return new ResponseEntity(response, HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<ReservationResponseModel>> getAllReservation() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ReservationDto> reservations = reservationService.getAllReservations();
		List<ReservationResponseModel> response = reservations.stream()
									.map(entity -> mapper.map(entity, ReservationResponseModel.class))
									.collect(Collectors.toList());
		return new ResponseEntity(response, HttpStatus.OK);
	}
}
