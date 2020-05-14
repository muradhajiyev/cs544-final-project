package edu.miu.cs544.medappointment.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import edu.miu.cs544.medappointment.repository.ReservationRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.service.ReservationService;
import edu.miu.cs544.medappointment.service.UserService;
import edu.miu.cs544.medappointment.shared.ReservationDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
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
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = {"ADMIN", "CHECKER"})
    public void createReservation_ValidInput_ThenReturnReservationResponseModel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        Appointment appointment = testAppointmentData();
        ReservationRequestModel requestModel = new ReservationRequestModel(Status.PENDING, appointment.getId());

        User userConsumer = userService.getAuthUser();
        if(userConsumer==null) throw new Exception("User not found!");

        UserResponseModel consumerRM = new UserResponseModel();
        consumerRM.setId(userConsumer.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User consumer = modelMapper.map(consumerRM, User.class);

        String jsonContent = mapper.writeValueAsString(requestModel);
        //String jsonContent = "{\"status\":\"PENDING\", \"appointment\":{\"id\":1, \"dateTime\": \"2020-05-23T10:00:00\", \"location\": \"Verill Hall #35\"}}";
        //System.out.println(jsonContent);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(jsonPath("$.appointment.location", is("Verill Hall #35")))
                .andExpect(jsonPath("$.appointment.provider.email", is("tmchecker@gmail.com")))
                .andExpect(jsonPath("$.consumer.email", is(userConsumer.getEmail())));
    }

    protected Appointment testAppointmentData(){
        //User userChecker = userRepository.getOne(2L);
        User userChecker = new User("TM Checker", "TM Checker", "tmchecker@gmail.com", "tmchecker", "123456");
        userChecker = userRepository.save(userChecker);

        Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35", userChecker);
        //appointment.setProvider(userChecker);

        return appointmentRepository.save(appointment);
        //return appointmentRepository.getOne(1L);
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = {"ADMIN", "CHECKER"})
    public void changeReservationStatus_ValidInput_ThenReturnReservationResponseModel() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ReservationRequestModel requestModel = new ReservationRequestModel();
        requestModel.setStatus(Status.CANCELED);
        requestModel.setAppointmentId(1L);
        String jsonContent = mapper.writeValueAsString(requestModel);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/reservations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("CANCELED")))
        ;
    }


    @Test
    public void cancelReservationBasedOnReservationId() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/reservation/cancel"+1L)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.status", is("CANCEL")));
    }


}
