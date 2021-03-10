package com.example.demo.service;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.pet.PetList;
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
                return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                        String.format(String.format(missingPet, "Owner"), "ID", null)), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            petList.setPetList(petRepository.findByOwnerId(ownerDto.getId()));
        } else {
            Optional<Owner> owner = ownerRepository.findByPhoneNumber(ownerDto.getPhoneNumber());
            if (owner.isEmpty()) {
                return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                        String.format(String.format(missingPet, "Owner"), "Phone Number", null)), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            petList.setPetList(petRepository.findByOwnerId(owner.get().getId()));
        }

        return new ResponseEntity<>(petList.toJSONString(), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<String> getById(Integer petId) throws JsonProcessingException {
        if (petId == null) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                    String.format(String.format(missingPet, "Pet ID"), "ID", null)), HttpStatus.NOT_FOUND);
        }
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.PET,
                    String.format(String.format(missingPet, "Owner"), "ID", null)), HttpStatus.NOT_FOUND);
        } else {
            Pet pet = petOptional.get();
            return new ResponseEntity<String>(pet.toJSONString(), HttpStatus.OK);
        }
    }

    /*
    * 1. Write this function
    * 2. Finish Pet UTs
    * 3. Set up functional Test Db
    * */

    @Override
    public ResponseEntity<String> createNewPet(PetDto petDto) {
        return null;
    }
}
