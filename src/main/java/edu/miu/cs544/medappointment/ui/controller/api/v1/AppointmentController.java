package edu.miu.cs544.medappointment.ui.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import io.swagger.annotations.ApiOperation;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation(value="Create a new appointment in the database", response=AppointmentResponseModel.class)
    @PostMapping
    public ResponseEntity<AppointmentResponseModel> createAppointment(@Valid @RequestBody AppointmentRequestModel model){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = mapper.map(model, AppointmentDto.class);

        Appointment appointment = appointmentService.createAppointment(appointmentDto);
        AppointmentResponseModel response = mapper.map(appointment, AppointmentResponseModel.class);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<AppointmentResponseModel> getAppointmentById(@PathVariable long id)
    {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		AppointmentDto appointment;
		AppointmentResponseModel response;
		try 
		{
			appointment = appointmentService.getAppointmentById(id);
			response = mapper.map(appointment, AppointmentResponseModel.class);
		}
		catch(Exception e)
		{//No appointment with given id
			response = null;
			e.printStackTrace();
		}
		return new ResponseEntity(response, HttpStatus.CREATED);
	}

}
