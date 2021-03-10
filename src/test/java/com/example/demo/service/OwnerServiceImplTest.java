package com.example.demo.service;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.repository.OwnerRepository;
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
class OwnerServiceImplTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerServiceImpl ownerService;

    private final Integer mockId = 1234;

    private final String mockPhoneNumber = "987654321";

    @Test
    void getById() throws JsonProcessingException {
        Owner owner = new Owner();
        owner.setId(mockId);
        Optional<Owner> optionalOwner = Optional.of(owner);
        when(ownerRepository.findById(mockId)).thenReturn(optionalOwner);
        ResponseEntity<String> ownerResponse = ownerService.getById(mockId);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByIdInvalidId() throws JsonProcessingException {
        ResponseEntity<String> ownerResponse = ownerService.getById(null);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getByIdOwnerDoesNotExist() throws JsonProcessingException {
        when(ownerRepository.findById(mockId)).thenReturn(Optional.empty());
        ResponseEntity<String> ownerResponse = ownerService.getById(mockId);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByPhoneNumberGreenPath() throws JsonProcessingException {
        Owner owner = new Owner();
        owner.setPhoneNumber(mockPhoneNumber);
        Optional<Owner> optionalOwner = Optional.of(owner);
        when(ownerRepository.findByPhoneNumber(mockPhoneNumber)).thenReturn(optionalOwner);
        ResponseEntity<String> ownerResponse = ownerService.getByPhoneNumber(mockPhoneNumber);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByPhoneNumberInvalidPhoneNumber() throws JsonProcessingException {
        ResponseEntity<String> ownerResponse = ownerService.getByPhoneNumber("");
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getByPhoneNumberOwnerDoesNotExist() throws JsonProcessingException {
        when(ownerRepository.findByPhoneNumber(mockPhoneNumber)).thenReturn(Optional.empty());
        ResponseEntity<String> ownerResponse = ownerService.getByPhoneNumber(mockPhoneNumber);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createNewOwner() throws JsonProcessingException {
        OwnerDto mockOwnerDto = new OwnerDto();
        mockOwnerDto.setFirstName("firstname");
        mockOwnerDto.setLastName("lastname");
        mockOwnerDto.setPhoneNumber("123456789");
        ResponseEntity<String> ownerResponse = ownerService.createNewOwner(mockOwnerDto);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void validateOwnerMissingInformation() throws JsonProcessingException {
        OwnerDto mockOwnerDto = new OwnerDto();
        mockOwnerDto.setFirstName("");
        mockOwnerDto.setLastName("");
        mockOwnerDto.setPhoneNumber("");
        ResponseEntity<String> ownerResponse = ownerService.createNewOwner(mockOwnerDto);
        Assertions.assertEquals(ownerResponse.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}