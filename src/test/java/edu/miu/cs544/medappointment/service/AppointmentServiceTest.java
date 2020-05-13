package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.Appointment;
import edu.miu.cs544.medappointment.entity.User;
import edu.miu.cs544.medappointment.repository.AppointmentRepository;
import edu.miu.cs544.medappointment.repository.UserRepository;
import edu.miu.cs544.medappointment.shared.AppointmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AppointmentServiceTest {

    private User user;
    private Appointment appointment;
    private List<Appointment> appointments;
    private Page<Appointment> appointmentsPage;

    @InjectMocks
    private AppointmentService appointmentService = new AppointmentServiceImpl();

    @Mock
    private AppointmentRepository appointmentRepository;

    // TODO. temporary till implement Security
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
        appointments=new ArrayList<>();
        appointments.add(appointment);
        appointmentsPage=new PageImpl<>(appointments);

        //mocking
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentRepository.findAll(any(Pageable.class))).thenReturn(appointmentsPage);
        when(appointmentRepository.count()).thenReturn(1L);
    }

    @Test
    void getAllAppointment_Pagable_ThenReturnPageAppointment() {

        Pageable page=PageRequest.of(0,20);
        Page<AppointmentDto> appointmentListResult=appointmentService.getAll(page, Optional.empty());
        assertEquals(appointmentsPage.getContent().get(0).getLocation(), appointmentListResult.getContent().get(0).getLocation());
        assertEquals(appointmentsPage.getContent().get(0).getDateTime(), appointmentListResult.getContent().get(0).getDateTime());
        assertEquals(appointmentsPage.getContent().get(0).getProvider(), appointmentListResult.getContent().get(0).getProvider());
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
}