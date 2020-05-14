package edu.miu.cs544.medappointment.ui.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.service.MyUserDetailsService;
import edu.miu.cs544.medappointment.service.ReservationService;
import edu.miu.cs544.medappointment.service.UserService;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import edu.miu.cs544.medappointment.ui.model.ReservationRequestModel;
import edu.miu.cs544.medappointment.ui.model.ReservationResponseModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
	public ResponseEntity<ReservationResponseModel> createReservation(@Valid @RequestBody ReservationRequestModel model) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		reservationDto.setAppointmentDto(appointmentService.getById(model.getAppointmentId()));
		ReservationDto reservation = reservationService.createReservation(reservationDto);

		ReservationResponseModel response = mapper.map(reservation, ReservationResponseModel.class);
		AppointmentResponseModel appointment = mapper.map(reservationDto.getAppointmentDto(), AppointmentResponseModel.class);
		response.setAppointment(appointment);

		return new ResponseEntity(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservationResponseModel> changeStatus(@PathVariable(value = "id") Long id, @Valid @RequestBody ReservationRequestModel model) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		/*System.out.println("IN SERVICE:::::::::::::");
		System.out.println(appointmentService.getById(model.getAppointmentId()));*/
		reservationDto.setAppointmentDto(appointmentService.getById(model.getAppointmentId()));
		//System.out.println(reservationDto.getAppointmentDto().getId());

		ReservationDto updated = reservationService.changeStatus(reservationDto, id);

		ReservationResponseModel response = mapper.map(updated, ReservationResponseModel.class);
		AppointmentResponseModel appointment = mapper.map(reservationDto.getAppointmentDto(), AppointmentResponseModel.class);
		response.setAppointment(appointment);

		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@ApiOperation(value="Get Reservations for the current user", response= ReservationResponseModel.class)
	@GetMapping
	public ResponseEntity<List<ReservationResponseModel>> getUserReservations()
	{
		
		List<ReservationResponseModel> response = convertToListReservationResponse(
													reservationService.viewUserReservations());
		
		return new ResponseEntity(response, HttpStatus.OK);
	}

	public List<ReservationResponseModel> convertToListReservationResponse(List<ReservationDto> resList)
	{
		if(null == resList)
			return null;
		else
		{
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return resList.stream()
					.map(entity -> mapper.map(entity, ReservationResponseModel.class))
					.collect(Collectors.toList());
		}
		
	}
	@PutMapping(value = "/cancel/{id}")
	private ResponseEntity<ReservationResponseModel> cancelReservationById(@PathVariable Long id) throws Exception {
		ReservationDto reservation = reservationService.cancelReservation(id);
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationResponseModel updatedResult = mapper.map(reservation, ReservationResponseModel.class);
		return new ResponseEntity(updatedResult, HttpStatus.OK);
	}
}
