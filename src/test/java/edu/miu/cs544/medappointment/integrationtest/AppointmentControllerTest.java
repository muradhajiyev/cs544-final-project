package edu.miu.cs544.medappointment.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs544.medappointment.ui.model.AppointmentRequestModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAppointment_givenUnauthenticatedUser_thenThrowsException() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/appointment")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"ADMIN"})
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

}