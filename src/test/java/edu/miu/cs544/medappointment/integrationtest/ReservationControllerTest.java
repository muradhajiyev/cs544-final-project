package edu.miu.cs544.medappointment.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.Reservation;
import edu.miu.cs544.medappointment.entity.Status;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.ui.model.ReservationRequestModel;
import edu.miu.cs544.medappointment.ui.model.UserResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void createReservation_ValidInput_ThenReturnReservationResponseModel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        /*mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);*/
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        Appointment appointment = testAppointmentData();
        ReservationRequestModel requestModel = new ReservationRequestModel(Status.PENDING, appointment);

        UserResponseModel consumerRM = new UserResponseModel();
        consumerRM.setId(3L);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User consumer = modelMapper.map(consumerRM, User.class);
        //System.out.println(userRepository.getOne(3L));
        requestModel.setConsumer( consumer);

        String jsonContent = mapper.writeValueAsString(requestModel);
        //String jsonContent = "{\"status\":\"PENDING\", \"appointment\":{\"id\":1, \"dateTime\": \"2020-05-23T10:00:00\", \"location\": \"Verill Hall #35\"}}";
        //System.out.println(jsonContent);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(jsonPath("$.appointment.location", is("Verill Hall #35")))
                .andExpect(jsonPath("$.appointment.provider.email", is("checker2@gmail.com")))
                .andExpect(jsonPath("$.consumer.email", is("anna@gmail.com")));
    }

    protected Appointment testAppointmentData(){
        //User userChecker = userRepository.getOne(2L);
        User userChecker = new User("TM Checker", "TM Checker", "checker2@gmail.com", "checker2", "123456");
        userChecker = userRepository.save(userChecker);

        Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35", userChecker);
        //appointment.setProvider(userChecker);

        return appointmentRepository.save(appointment);
        //return appointmentRepository.getOne(1L);
    }


}
