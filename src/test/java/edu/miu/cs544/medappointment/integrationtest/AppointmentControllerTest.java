package edu.miu.cs544.medappointment.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserRepository userRepository;
    
    
    @Test
    public void createAppointment_ValidInput_ThenReturnAppointmentResponseModel() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        AppointmentRequestModel requestModel = new AppointmentRequestModel(LocalDateTime.now(), "Location");
//        String jsonContent = mapper.writeValueAsString(requestModel);
        String jsonContent = "{\"dateTime\": \"2020-05-23T10:00:00\", \"location\": \"Location\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
        
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.location", is("Location")));
    }
    
    @Test
    public void getAppointmentById_Id_ThenReturnAppointmentResponseModel() throws Exception
    {
    	User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        userRepository.save(user);
        Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35", user);
        Appointment result = appointmentRepository.save(appointment);
        long id = result.getId();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/appointment/" + id);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.location", is("Verill Hall #35")));
    }

}