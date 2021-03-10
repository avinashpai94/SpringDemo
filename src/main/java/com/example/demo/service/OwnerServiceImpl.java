package com.example.demo.service;

import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.repository.OwnerRepository;
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
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetMapper petMapper;

    private static final String missingOwner = "No Owner with %s as %s";

    @Override
    public ResponseEntity<String> getById(Integer ownerId) throws JsonProcessingException {
        if (ownerId == null) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.OWNER,
                    String.format(missingOwner, "ID", null)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.OWNER,
                    String.format(missingOwner, "ID", ownerId.toString())), HttpStatus.NOT_FOUND);
        } else {
            Owner owner = ownerOptional.get();
            return new ResponseEntity<String>(owner.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> getByPhoneNumber(String phoneNumber) throws JsonProcessingException {
        if (phoneNumber.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.OWNER,
                    String.format(missingOwner, "Phone Number", phoneNumber)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Owner> ownerOptional = ownerRepository.findByPhoneNumber(phoneNumber);
        if (ownerOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.OWNER,
                    String.format(missingOwner, "Phone Number", phoneNumber)), HttpStatus.NOT_FOUND);
        } else {
            Owner owner = ownerOptional.get();
            return new ResponseEntity<String>(owner.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> createNewOwner(OwnerDto ownerDto) throws JsonProcessingException {
        List<String> validations = validateOwner(new Owner(ownerDto));
        if (!validations.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.OWNER,
                    validations.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            //map dto to object and save in table
            Owner owner = new Owner(ownerDto);
            ownerRepository.save(owner);
            return new ResponseEntity<>(owner.toJSONString(), HttpStatus.OK);
        }
    }

    public List<String> validateOwner (Owner owner) {
        List<String> validations = new ArrayList<>();
        if (owner.getFirstName().isEmpty() || owner.getLastName().isEmpty()) {
            validations.add("Missing First/Last name");
        }
        if (owner.getPhoneNumber().isEmpty()) {
            validations.add("Missing Phone Number");
        }
        return validations;
    }
}
