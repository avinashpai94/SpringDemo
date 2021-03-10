package com.example.demo.service;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.models.pet.Pet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PetService {

    ResponseEntity<String> getByOwner(OwnerDto ownerDto) throws JsonProcessingException;

    ResponseEntity<String> getById(Integer petId) throws JsonProcessingException;

    ResponseEntity<String> createNewPet(PetDto petDto) throws JsonProcessingException;
}
