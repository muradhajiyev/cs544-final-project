package edu.miu.cs544.medappointment.ui.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.service.MyUserDetailsService;
import edu.miu.cs544.medappointment.service.ReservationService;
import edu.miu.cs544.medappointment.service.UserService;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import edu.miu.cs544.medappointment.ui.model.ReservationRequestModel;
import edu.miu.cs544.medappointment.ui.model.ReservationResponseModel;
import edu.miu.cs544.medappointment.ui.model.UserResponseModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private AppointmentService appointmentService;

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STUDENT')")
	@ApiOperation(value="Create a new reservation in the database", response= ReservationResponseModel.class)
	@PostMapping
	public ResponseEntity<ReservationResponseModel> createReservation(@Valid @RequestParam("appointmentId") Long appointmentId) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		ReservationDto reservationDto = new ReservationDto();
		//reservationDto.setAppointment(appointmentService.getById(model.getAppointmentId()));
		reservationDto.setAppointment(appointmentService.getById(appointmentId));
		ReservationDto reservation = reservationService.createReservation(reservationDto);

		ReservationResponseModel response = mapper.map(reservation, ReservationResponseModel.class);
		AppointmentResponseModel appointment = mapper.map(reservationDto.getAppointment(), AppointmentResponseModel.class);
		response.setAppointment(appointment);
		UserResponseModel consumer = mapper.map(reservation.getConsumer(), UserResponseModel.class);
		response.setConsumer(consumer);

		return new ResponseEntity(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservationResponseModel> changeStatus(@PathVariable(value = "id") Long id, @RequestParam("status") Status status) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		ReservationDto reservationDto = new ReservationDto();
		ReservationDto updated = reservationService.changeReservationStatus(status, id);

		ReservationResponseModel response = mapper.map(updated, ReservationResponseModel.class);
		//AppointmentResponseModel appointment = mapper.map(updated.getAppointmentDto(), AppointmentResponseModel.class);
		//response.setAppointment(appointment);

		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@ApiOperation(value="Get Reservations for the current user", response= ReservationResponseModel.class)
	@GetMapping
	public ResponseEntity<List<ReservationResponseModel>> getUserReservations(Pageable pageable)
	{
		Page<ReservationResponseModel> response = reservationService.getUserReservations(pageable).map(r -> convertToReservationResponse(r));
		return new ResponseEntity(response, HttpStatus.OK);
	}

	public ReservationResponseModel convertToReservationResponse(ReservationDto reservationDto)
	{
		if(reservationDto == null)
			return null;

			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return mapper.map(reservationDto, ReservationResponseModel.class);
	}

//	@PutMapping(value = "/{id}/cancel")
//	@ApiOperation(value="Cancel the reservation", response= ReservationResponseModel.class)
//	public ResponseEntity<ReservationResponseModel> cancelReservationById(@PathVariable Long id) throws Exception {
//		ReservationDto reservation = reservationService.cancelReservation(id);
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//		ReservationResponseModel updatedResult = mapper.map(reservation, ReservationResponseModel.class);
//		return new ResponseEntity(updatedResult, HttpStatus.OK);
//	}

	@ApiOperation(value="Get Reservation Detail for the current user", response= ReservationResponseModel.class)
	@GetMapping("/{id}")
	public ResponseEntity<ReservationResponseModel> getReservation(@PathVariable Long id) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDetail = reservationService.getReservation(id);

		ReservationResponseModel response = mapper.map(reservationDetail, ReservationResponseModel.class);
		return new ResponseEntity(response, HttpStatus.OK);
	}
}
