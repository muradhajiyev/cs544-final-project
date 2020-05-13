package edu.miu.cs544.medappointment.integrationtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAppointment_ValidInput_ThenReturnAppointmentResponseModel() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        AppointmentRequestModel requestModel = new AppointmentRequestModel(LocalDateTime.now(), "Location");
//        String jsonContent = mapper.writeValueAsString(requestModel);
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

    @Test
    public void getAllAppointment_Pagable_ThenReturnPageAppointment() throws Exception{
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllAppointment_ThenReturnListofAppointment() throws Exception{
        mockMvc.perform(get("/api/v1/appointments?fetch-all=true"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllCount_ThenReturnCountNumber() throws Exception {
        mockMvc.perform(get("/api/v1/appointments/count").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}