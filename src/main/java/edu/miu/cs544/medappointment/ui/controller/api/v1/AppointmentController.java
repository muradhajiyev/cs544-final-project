package edu.miu.cs544.medappointment.ui.controller.api.v1;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.service.AppointmentService;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import edu.miu.cs544.medappointment.ui.model.AppointmentResponseModel;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments")
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

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseModel>> getAll(Pageable page){
        Page<AppointmentResponseModel> result=new PageImpl<AppointmentResponseModel>(appointmentService.getAll(page)
                .getContent().stream()
                .map(appointment -> convertToAppointmentResponseModel(appointment))
                .collect(Collectors.toList())
        );
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping(params = "fetch-all=true")
    public ResponseEntity<List<AppointmentResponseModel>> getAll(){
        List<AppointmentResponseModel> result=appointmentService.getAll().stream()
                .map(appointment -> convertToAppointmentResponseModel(appointment))
                .collect(Collectors.toList());
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAllCount() {
        Long result=appointmentService.getAllCount();
        return new ResponseEntity(result,HttpStatus.OK);
    }



    private AppointmentResponseModel convertToAppointmentResponseModel(AppointmentDto appointment) {
        if(appointment!=null) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            AppointmentResponseModel appointmentResponseModel = modelMapper.map(appointment, AppointmentResponseModel.class);
            return appointmentResponseModel;
        }else{
            return null;
        }
    }
}
