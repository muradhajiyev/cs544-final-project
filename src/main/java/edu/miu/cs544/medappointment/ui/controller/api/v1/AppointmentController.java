package edu.miu.cs544.medappointment.ui.controller.api.v1;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CHECKER')")
    @PostMapping
    @ApiOperation(value="Create a new appointment in the database", response=AppointmentResponseModel.class)
    public ResponseEntity<AppointmentResponseModel> createAppointment(@Valid @RequestBody AppointmentRequestModel model){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = mapper.map(model, AppointmentDto.class);

        AppointmentDto appointment = appointmentService.createAppointment(appointmentDto);
        AppointmentResponseModel response = mapper.map(appointment, AppointmentResponseModel.class);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @ApiOperation(value="Get an appointment by id", response=AppointmentResponseModel.class)
	public ResponseEntity<AppointmentResponseModel> getAppointmentById(@PathVariable long id)
    {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointment = appointmentService.getById(id);
        AppointmentResponseModel response = mapper.map(appointment, AppointmentResponseModel.class);
		return new ResponseEntity(response, HttpStatus.OK);
	}

    @GetMapping
    @ApiOperation(value="Get All Appoinments", response=AppointmentResponseModel.class, responseContainer = "List")
    public ResponseEntity<Page<AppointmentResponseModel>> getAllAppointments(Pageable page, @ApiParam(name =  "status", type = "String", required = false) @RequestParam Optional<Status> status){
        Page<AppointmentResponseModel> result = appointmentService.getAll(page,status).map(appointment -> convertToAppointmentResponseModel(appointment));
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/available")
    @ApiOperation(value="Get All Available Appointments", response=AppointmentResponseModel.class, responseContainer = "List")
    public ResponseEntity<Page<AppointmentResponseModel>> getAllAvailableAppointments(Pageable page){
        Page<AppointmentResponseModel> result = appointmentService.getAllAvailable(page).map(appointment -> convertToAppointmentResponseModel(appointment));
        return new ResponseEntity(result,HttpStatus.OK);
    }


    @GetMapping("/{id}/reservations")
    public ResponseEntity<Page> getAppointmentReservations(Pageable page, @PathVariable("id") Long appointmentId){
        return ResponseEntity.ok(appointmentService.getAppointmentReservations(appointmentId, page));
    }


    @PutMapping("/{id}")
    @ApiOperation(value="Update an appointment by id", response=AppointmentResponseModel.class)
    public ResponseEntity<AppointmentResponseModel> updateAppointment(@PathVariable Long id, @Valid @RequestBody AppointmentRequestModel model) throws Exception {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = mapper.map(model, AppointmentDto.class);

        Appointment appointment = appointmentService.updateAppointmentById(id, appointmentDto);
        AppointmentResponseModel response = mapper.map(appointment, AppointmentResponseModel.class);
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value="Delete an appointment by id")
    public ResponseEntity deleteAppointment(@PathVariable long id){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }

    private AppointmentResponseModel convertToAppointmentResponseModel(AppointmentDto appointment) {
        if(appointment == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentResponseModel appointmentResponseModel = modelMapper.map(appointment, AppointmentResponseModel.class);
        return appointmentResponseModel;
    }

}
