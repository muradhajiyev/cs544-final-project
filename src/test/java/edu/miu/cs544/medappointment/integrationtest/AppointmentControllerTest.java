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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    
    
    @Test
    public void createAppointment_givenUnauthenticatedUser_thenThrowsException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/appointment")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", authorities = {"ADMIN", "CHECKER"})
    public void createAppointment_ValidInput_ThenReturnAppointmentResponseModel() throws Exception {
        String jsonContent = "{\"dateTime\": \"2020-05-23T10:00:00\", \"location\": \"Location\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
        
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.location", is("Location")));
    }
    /*
    @Test
    @WithMockUser(username = "admin", password = "123456")
    public void getAppointmentById_Id_ThenReturnAppointmentResponseModel() throws Exception
    {
    	User user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");
        userRepository.save(user);
        Appointment appointment = new Appointment(LocalDateTime.now(),"Verill Hall #35", user);
        Appointment result = appointmentRepository.save(appointment);
        long id = result.getId();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/appointment/" + id);
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.location", is("Verill Hall #35")));
    }*/


    @Test
    @WithMockUser(username = "admin", password = "123456")
    public void getAllAppointment_Pagable_ThenReturnPageAppointment() throws Exception{
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", password = "123456")
    public void getAllAppointment_ThenReturnListofAppointment() throws Exception{
        mockMvc.perform(get("/api/v1/appointments?fetch-all=true"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", password = "123456")
    public void getAllCount_ThenReturnCountNumber() throws Exception {
        mockMvc.perform(get("/api/v1/appointments/count").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}