package com.example.demo.service;

import com.example.demo.dto.models.VetDto;
import com.example.demo.models.vet.Vet;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VetServiceImplTest {

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetServiceImpl vetService;

    private final Integer mockId = 1234;

    private final String mockPhoneNumber = "987654321";

    @Test
    void getByIdGreenPath() throws JsonProcessingException {
        Vet vet = new Vet();
        vet.setId(mockId);
        Optional<Vet> vetOptional = Optional.of(vet);
        when(vetRepository.findById(mockId)).thenReturn(vetOptional);
        ResponseEntity<String> vetResponse = vetService.getById(mockId);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByIdInvalidId() throws JsonProcessingException {
        ResponseEntity<String> vetResponse = vetService.getById(null);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getByIdVetDoesNotExist() throws JsonProcessingException {
        when(vetRepository.findById(mockId)).thenReturn(Optional.empty());
        ResponseEntity<String> vetResponse = vetService.getById(mockId);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByPhoneNumberGreenPath() throws JsonProcessingException {
        Vet vet = new Vet();
        vet.setPhoneNumber(mockPhoneNumber);
        Optional<Vet> vetOptional = Optional.of(vet);
        when(vetRepository.findVetByPhoneNumber(mockPhoneNumber)).thenReturn(vetOptional);
        ResponseEntity<String> vetResponse = vetService.getByPhoneNumber(mockPhoneNumber);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByPhoneNumberInvalidPhoneNumber() throws JsonProcessingException {
        ResponseEntity<String> vetResponse = vetService.getByPhoneNumber("");
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getByPhoneNumberVetDoesNotExist() throws JsonProcessingException {
        when(vetRepository.findVetByPhoneNumber(mockPhoneNumber)).thenReturn(Optional.empty());
        ResponseEntity<String> vetResponse = vetService.getByPhoneNumber(mockPhoneNumber);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createNewVet() throws JsonProcessingException {
        VetDto mockVetDto = new VetDto();
        mockVetDto.setFirstName("firstname");
        mockVetDto.setLastName("lastname");
        mockVetDto.setPhoneNumber("123456789");
        ResponseEntity<String> vetResponse = vetService.createNewVet(mockVetDto);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void validateVetMissingInformation() throws JsonProcessingException {
        VetDto mockVetDto = new VetDto();
        mockVetDto.setFirstName("");
        mockVetDto.setLastName("");
        mockVetDto.setPhoneNumber("");
        ResponseEntity<String> vetResponse = vetService.createNewVet(mockVetDto);
        Assertions.assertEquals(vetResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}