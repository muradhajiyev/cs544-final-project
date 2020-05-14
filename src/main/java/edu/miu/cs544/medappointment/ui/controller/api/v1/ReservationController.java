package edu.miu.cs544.medappointment.ui.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.service.ReservationService;
import edu.miu.cs544.medappointment.shared.ReservationDto;
import edu.miu.cs544.medappointment.ui.model.ReservationRequestModel;
import edu.miu.cs544.medappointment.ui.model.ReservationResponseModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private AppointmentService appointmentService;

	@ApiOperation(value="Create a new reservation in the database", response= ReservationResponseModel.class)
	@PostMapping
	public ResponseEntity<ReservationResponseModel> createReservation(@Valid @RequestBody ReservationRequestModel model) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		reservationDto.setAppointmentDto(appointmentService.getById(model.getAppointmentId()));

		System.out.println("CONTROLLER::::::::::::::");
		System.out.println(reservationDto.getAppointmentDto().getId());

		ReservationDto reservation = reservationService.createReservation(reservationDto);
		ReservationResponseModel response = mapper.map(reservation, ReservationResponseModel.class);

		return new ResponseEntity(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReservationResponseModel> changeStatus(@PathVariable(value = "id") Long id, @Valid @RequestBody ReservationRequestModel model) throws Exception {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		System.out.println("IN SERVICE:::::::::::::");
		System.out.println(appointmentService.getById(model.getAppointmentId()));
		reservationDto.setAppointmentDto(appointmentService.getById(model.getAppointmentId()));
		System.out.println(reservationDto.getAppointmentDto().getId());

		ReservationDto updated = reservationService.changeStatus(reservationDto, id);
		ReservationResponseModel response = mapper.map(updated, ReservationResponseModel.class);

		return new ResponseEntity(response, HttpStatus.CREATED);
	}
	@PutMapping(value = "/cancel/{reservationId}")
	private ResponseEntity<ReservationResponseModel> cancelReservationById(@PathVariable Long reservationId) throws Exception {
		ReservationDto reservation = reservationService.cancelReservation(reservationId);
		ReservationRequestModel model = new ReservationRequestModel();
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReservationDto reservationDto = mapper.map(model, ReservationDto.class);
		ReservationResponseModel updatedResult = mapper.map(reservation, ReservationResponseModel.class);
		return new ResponseEntity(updatedResult, HttpStatus.OK);
	}
}
