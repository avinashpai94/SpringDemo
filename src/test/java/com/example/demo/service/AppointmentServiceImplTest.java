package com.example.demo.service;

import com.example.demo.dto.mapper.OwnerMapper;
import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.vet.Vet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.VetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    AppointmentServiceImpl appointmentService;

    private final Integer mockAppointmentId = 1234;

    private final Integer mockVetId = 9876;

    private final Integer mockPetId = 4321;

    private final Integer mockOwnerId = 6789;

    private final String mockAppointmentTime = "mockAppointmentTime";

    private final String mockAppointmentRequest = "{\n" +
            "    \"vetId\" : "+mockVetId+",\n" +
            "    \"ownerId\": "+mockOwnerId+",\n" +
            "    \"petId\": "+mockPetId+",\n" +
            "    \"timeSlot\": \""+mockAppointmentTime+"\" ,\n" +
            "    \"description\": \"Unit Test Appointment\"\n" +
            "}";

    @Test
    void getById() throws JsonProcessingException {
        Appointment appointment = new Appointment();
        appointment.setId(mockAppointmentId);
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findAppointmentById(mockAppointmentId)).thenReturn(appointmentOptional);
        ResponseEntity<String> appointmentResponse = appointmentService.getById(mockAppointmentId);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByIdAppointmentDoesNotExist() throws JsonProcessingException {
        when(appointmentRepository.findAppointmentById(mockAppointmentId)).thenReturn(Optional.empty());
        ResponseEntity<String> appointmentResponse = appointmentService.getById(mockAppointmentId);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByVetId() throws JsonProcessingException {
        VetDto vetDto = new VetDto();
        vetDto.setId(mockVetId);
        Appointment mockAppointment = new Appointment();
        when(appointmentRepository.findAppointmentByVetId(mockVetId)).thenReturn(List.of(mockAppointment));
        ResponseEntity<String> appointmentResponse = appointmentService.getByVetId(vetDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByVetIdAppointmentDoesNotExist() throws JsonProcessingException {
        VetDto vetDto = new VetDto();
        vetDto.setId(mockVetId);
        when(appointmentRepository.findAppointmentByVetId(mockVetId)).thenReturn(List.of());
        ResponseEntity<String> appointmentResponse = appointmentService.getByVetId(vetDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByPetId() throws JsonProcessingException {
        PetDto petDto = new PetDto();
        petDto.setId(mockPetId);
        Appointment mockAppointment = new Appointment();
        when(appointmentRepository.findAppointmentByPetId(mockPetId)).thenReturn(List.of(mockAppointment));
        ResponseEntity<String> appointmentResponse = appointmentService.getByPetId(petDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByPetIdAppointmentDoesNotExist() throws JsonProcessingException {
        PetDto petDto = new PetDto();
        petDto.setId(mockPetId);
        when(appointmentRepository.findAppointmentByPetId(mockPetId)).thenReturn(List.of());
        ResponseEntity<String> appointmentResponse = appointmentService.getByPetId(petDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByOwnerId() throws JsonProcessingException {
        Owner owner = new Owner();
        owner.setId(mockOwnerId);
        OwnerDto ownerDto = OwnerMapper.toOwnerDto(owner);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(owner));

        Pet pet = new Pet();
        pet.setId(mockPetId);
        Appointment appointment = new Appointment();
        pet.setAppointments(List.of(appointment));
        when(petRepository.findByOwnerId(mockOwnerId)).thenReturn(List.of(pet));

        ResponseEntity<String> appointmentResponse = appointmentService.getByOwnerId(ownerDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByOwnerIdAppointmentDoesNotExist() throws JsonProcessingException {
        Owner owner = new Owner();
        owner.setId(mockOwnerId);
        OwnerDto ownerDto = OwnerMapper.toOwnerDto(owner);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(owner));

        Pet pet = new Pet();
        pet.setId(mockPetId);
        when(petRepository.findByOwnerId(mockOwnerId)).thenReturn(List.of(pet));

        ResponseEntity<String> appointmentResponse = appointmentService.getByOwnerId(ownerDto);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByTime() throws JsonProcessingException {
        String mockStartTime = "mockStartTime";
        String mockEndTime = "mockEndTime";
        when(appointmentRepository.findAppointmentsByTimeSlot(mockStartTime, mockEndTime)).thenReturn(List.of(new Appointment()));
        ResponseEntity<String> responseEntity = appointmentService.getByTime(mockStartTime, mockEndTime);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getTimeAppointmentDoesNotExist() throws JsonProcessingException {
        String mockStartTime = "mockStartTime";
        String mockEndTime = "mockEndTime";
        when(appointmentRepository.findAppointmentsByTimeSlot(mockStartTime, mockEndTime)).thenReturn(List.of());
        ResponseEntity<String> responseEntity = appointmentService.getByTime(mockStartTime, mockEndTime);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void bookAppointment() throws JsonProcessingException {
        Appointment appointment = new Appointment();
        final String nonClashingTime = "nonClashingTime";
        appointment.setTimeSlot(nonClashingTime);
        when(appointmentRepository.findAppointmentsByMembers(mockVetId, mockPetId, mockOwnerId)).
                thenReturn(List.of(appointment));
        when(vetRepository.findVetById(mockVetId)).thenReturn(Optional.of(new Vet()));
        when(petRepository.findById(mockPetId)).thenReturn(Optional.of(new Pet()));
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(new Owner()));
        ResponseEntity<String> responseEntity = appointmentService.bookAppointment(mockAppointmentRequest);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void bookAppointmentTimeConflict() throws JsonProcessingException {
        Appointment appointment = new Appointment();
        appointment.setTimeSlot(mockAppointmentTime);
        when(appointmentRepository.findAppointmentsByMembers(mockVetId, mockPetId, mockOwnerId)).
                thenReturn(List.of(appointment));
        when(vetRepository.findVetById(mockVetId)).thenReturn(Optional.of(new Vet()));
        when(petRepository.findById(mockPetId)).thenReturn(Optional.of(new Pet()));
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(new Owner()));
        ResponseEntity<String> responseEntity = appointmentService.bookAppointment(mockAppointmentRequest);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void cancelAppointment() throws JsonProcessingException {
        Appointment appointment = new Appointment();
        appointment.setId(mockAppointmentId);
        Optional<Appointment> appointmentOptional = Optional.of(appointment);
        when(appointmentRepository.findAppointmentById(mockAppointmentId)).thenReturn(appointmentOptional);
        ResponseEntity<String> appointmentResponse = appointmentService.cancelAppointment(mockAppointmentId);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void cancelAppointmentDoesNotExist() throws JsonProcessingException {
        when(appointmentRepository.findAppointmentById(mockAppointmentId)).thenReturn(Optional.empty());
        ResponseEntity<String> appointmentResponse = appointmentService.cancelAppointment(mockAppointmentId);
        Assertions.assertEquals(appointmentResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}