package com.example.demo.service;

import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.pet.PetList;
import com.example.demo.models.pet.PetType;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.utils.exception.EntityType;
import com.example.demo.utils.exception.ExceptionClass;
import com.example.demo.utils.exception.ExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PetServiceImpl implements PetService{

    @Autowired
    PetRepository petRepository;

    @Autowired
    OwnerRepository ownerRepository;

    private final String missingPet = "Pet cannot be associated with %s";

    @Override
    public ResponseEntity<String> getByOwner(OwnerDto ownerDto) throws JsonProcessingException {
        if (ownerDto.getId() == null && ownerDto.getPhoneNumber() == null) {
            return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                    String.format(String.format(missingPet, "Owner"), "ID/Phone Number", null)), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PetList petList = new PetList();

        if (ownerDto.getId() != null) {
            Optional<Owner> owner = ownerRepository.findById(ownerDto.getId());
            if (owner.isEmpty()) {
                return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.PET,
                        String.format(String.format(missingPet, "Owner"), "ID", null)), HttpStatus.NOT_FOUND);
            }
            petList.setPetList(petRepository.findByOwnerId(ownerDto.getId()));
        } else {
            Optional<Owner> owner = ownerRepository.findByPhoneNumber(ownerDto.getPhoneNumber());
            if (owner.isEmpty()) {
                return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.PET,
                        String.format(String.format(missingPet, "Owner"), "Phone Number", null)), HttpStatus.NOT_FOUND);
            }
            petList.setPetList(petRepository.findByOwnerId(owner.get().getId()));
        }

        return new ResponseEntity<>(petList.toJSONString(), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> getById(Integer petId) throws JsonProcessingException {
        if (petId == null) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                    String.format(String.format(missingPet, "Pet ID"), "ID", null)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.PET,
                    "No Pet associated with ID: "+ petId.toString()), HttpStatus.NOT_FOUND);
        } else {
            Pet pet = petOptional.get();
            return new ResponseEntity<String>(pet.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> createNewPet(PetDto petDto) throws JsonProcessingException {
        List<String> validations = validatePet(petDto);
        if (!validations.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                    validations.toString()), HttpStatus.NOT_FOUND);
        }
        //map dto to object and save in table
        Pet pet = new Pet(petDto);
        petRepository.save(pet);
        return new ResponseEntity<>(pet.toJSONString(), HttpStatus.OK);
    }


    public List<String> validatePet(PetDto petDto) {
        List<String> validations = new ArrayList<>();
        if (petDto.getName().isEmpty()) {
            validations.add("Pet missing name");
        }

        if (petDto.getOwnerId() == null) {
            validations.add("No owner associated with pet");
        } else {
            Optional<Owner> owner = ownerRepository.findById(petDto.getOwnerId());
            if (owner.isEmpty()) {
                validations.add("No such owner");
            }
        }

        if (!PetType.contains(petDto.getType())) {
            validations.add("Invalid Pet Type");
        }

        if (petDto.getBirthDate().isEmpty()) {
            validations.add("Pet missing birthday");
        }

        return validations;
    }
}
