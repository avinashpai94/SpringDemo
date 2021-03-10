package com.example.demo.service;

import com.example.demo.dto.mapper.OwnerMapper;
import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.PetDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
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

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

    @Mock
    PetRepository petRepository;

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    PetServiceImpl petService;

    private final Integer mockPetId = 1234;

    private final Integer mockOwnerId = 4321;

    private final String mockPetName = "mockPetName";

    private final String mockPetBirthDate = "mockPetBirthdate";

    @Test
    void getByOwnerGreenPath() throws JsonProcessingException {
        Owner mockOwner = new Owner();
        mockOwner.setId(mockOwnerId);
        Pet mockPet = new Pet();
        mockPet.setId(mockPetId);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(mockOwner));
        when(petRepository.findByOwnerId(mockOwnerId)).thenReturn(List.of(mockPet));
        ResponseEntity<String> responseEntity = petService.getByOwner(OwnerMapper.toOwnerDto(mockOwner));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByOwnerMissingContact() throws JsonProcessingException {
        Owner mockOwner = new Owner();
        ResponseEntity<String> responseEntity = petService.getByOwner(OwnerMapper.toOwnerDto(mockOwner));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getByOwnerNoSuchOwner() throws JsonProcessingException {
        Owner mockOwner = new Owner();
        mockOwner.setId(mockOwnerId);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.empty());
        ResponseEntity<String> responseEntity = petService.getByOwner(OwnerMapper.toOwnerDto(mockOwner));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByOwnerNoPetsAssociated() throws JsonProcessingException {
        Owner mockOwner = new Owner();
        mockOwner.setId(mockOwnerId);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(mockOwner));
        when(petRepository.findByOwnerId(mockOwnerId)).thenReturn(List.of());
        ResponseEntity<String> responseEntity = petService.getByOwner(OwnerMapper.toOwnerDto(mockOwner));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByIdGreenPath() throws JsonProcessingException {
        Pet mockPet = new Pet();
        mockPet.setId(mockPetId);
        when(petRepository.findById(mockPetId)).thenReturn(Optional.of(mockPet));
        ResponseEntity<String> responseEntity = petService.getById(mockPetId);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getByIdInvalidId() throws JsonProcessingException {
        when(petRepository.findById(mockPetId)).thenReturn(Optional.empty());
        ResponseEntity<String> responseEntity = petService.getById(mockPetId);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createNewPetGreenPath() throws JsonProcessingException {
        Pet pet = new Pet();
        pet.setName(mockPetName);
        pet.setBirthDate(mockPetBirthDate);
        pet.setOwnerId(mockOwnerId);
        Owner owner = new Owner();
        owner.setId(mockOwnerId);
        when(ownerRepository.findById(mockOwnerId)).thenReturn(Optional.of(owner));
        ResponseEntity<String> responseEntity = petService.createNewPet(PetMapper.toPetDto(pet));
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void validatePetFailures() {
        PetDto petDto = new PetDto();
        petDto.setId(null);
        petDto.setOwnerId(null);
        petDto.setBirthDate("");
        petDto.setName("");
        List<String> validations = petService.validatePet(petDto);
        Assertions.assertNotEquals(validations.size(), 0);
    }
}