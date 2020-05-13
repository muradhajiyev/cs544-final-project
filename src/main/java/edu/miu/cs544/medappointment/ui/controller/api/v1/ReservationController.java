package edu.miu.cs544.medappointment.ui.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Reservation;
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
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation(value="Create a new reservation in the database", response= ReservationResponseModel.class)
    @PostMapping
    public ResponseEntity<ReservationResponseModel> createAppointment(@Valid @RequestBody ReservationRequestModel model){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ReservationDto reservationDto = mapper.map(model, ReservationDto.class);

        Reservation reservation = reservationService.createReservation(reservationDto);
        ReservationResponseModel response = mapper.map(reservation, ReservationResponseModel.class);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cancel/{reservationId}")
    private ResponseEntity<ReservationResponseModel> cancelReservationById(@PathVariable Long reservationId, @Valid @RequestBody ReservationRequestModel model) throws Exception {
        ReservationDto reservation = reservationService.cancelReservation(reservationId);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ReservationDto reservationDto = mapper.map(model, ReservationDto.class);

        ReservationResponseModel updatedResult = mapper.map(reservation, ReservationResponseModel.class);

        return new ResponseEntity(updatedResult, HttpStatus.OK);
    }

}
