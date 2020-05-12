package edu.miu.cs544.medappointment.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentResponseModel> createAppointment(@Valid @RequestBody AppointmentRequestModel model){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = mapper.map(model, AppointmentDto.class);

        Appointment appointment = appointmentService.createAppointment(appointmentDto);
        AppointmentResponseModel response = mapper.map(appointment, AppointmentResponseModel.class);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

}
