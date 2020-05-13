package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AppointmentServiceTest {

//    @TestConfiguration
//    static class AppointmentServiceImplTestContextConfiguration{
//        @Bean
//        public AppointmentService appointmentService(){
//            return new AppointmentServiceImpl();
//        }
//    }

    private User user;
    private Appointment appointment;

//    @Autowired
    @InjectMocks
    private AppointmentService appointmentService = new AppointmentServiceImpl();

//    @MockBean
    @Mock
    private AppointmentRepository appointmentRepository;

    // temporary till implement Security
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        //given
        user = new User("TM Checker", "TM Checker", "checker@gmail.com", "checker", "123456");

        appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setLocation("McLaughlin");
        appointment.setProvider(user);

        //mocking
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentRepository.findById(new Long(1))).thenReturn(Optional.of(appointment));
    }

    @Test
    void createAppointment_AppointmentEntity_ThenReturnSavedAppointment() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);


        Appointment created = appointmentService.createAppointment(appointmentDto);

        assertEquals(user.getEmail(), created.getProvider().getEmail());
        assertEquals(created.getLocation(), created.getLocation());
    }
    
    @Test
    void getAppointmentById_Id_ThenReturnAppointment() throws Exception 
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);


        AppointmentDto returned = appointmentService.getAppointmentById(1);

        assertEquals(returned.getProvider().getEmail(), appointment.getProvider().getEmail());
        assertEquals(returned.getDateTime(), appointment.getDateTime());
    }
}