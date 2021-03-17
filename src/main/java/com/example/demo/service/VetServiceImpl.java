package com.example.demo.service;

import com.example.demo.dto.models.VetDto;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.vet.SpecialtyList;
import com.example.demo.models.vet.Vet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.VetRepository;
import com.example.demo.utils.exception.EntityType;
import com.example.demo.utils.exception.ExceptionClass;
import com.example.demo.utils.exception.ExceptionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class VetServiceImpl implements VetService {

    @Autowired
    VetRepository vetRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String missingVet = "No Vet with %s as %s";

    @Override
    public ResponseEntity<String> getById(Integer vetId) throws JsonProcessingException {
        if (vetId == null) {
            return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.VET,
                    String.format(missingVet, "ID", null)), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Vet> vetOptional = vetRepository.findById(vetId);
        if (vetOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.VET,
                    String.format(missingVet, "ID", vetId.toString())), HttpStatus.NOT_FOUND);
        } else {
            Vet vet = vetOptional.get();
            return new ResponseEntity<String>(vet.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> getByPhoneNumber(String phoneNumber) throws JsonProcessingException {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return new ResponseEntity<>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.VET,
                    String.format(missingVet, "Phone Number", "Blank")), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Vet> vetOptional = vetRepository.findVetByPhoneNumber(phoneNumber);
        if (vetOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.VET,
                    String.format(missingVet, "Phone Number", phoneNumber)), HttpStatus.NOT_FOUND);
        } else {
            Vet vet = vetOptional.get();
            return new ResponseEntity<String>(vet.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> createNewVet(VetDto vetDto) throws JsonProcessingException {
        List<String> validations = validateVet(new Vet(vetDto));
        if (!validations.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.VET,
                    validations.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            Optional<Vet> vetOptional = vetRepository.findVetByPhoneNumber(vetDto.getPhoneNumber());
            if (vetOptional.isPresent()) {
                return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.VET,
                        "Phone Number Registered to User"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            //map dto to object and save in table
            Vet vet = new Vet(vetDto);
            vetRepository.save(vet);
            return new ResponseEntity<>(vet.toJSONString(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> addVetSpecialities(Integer vetId, SpecialtyList specialityList) throws JsonProcessingException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<String> validateVet (Vet vet) {
        List<String> validations = new ArrayList<>();
        if (vet.getFirstName().isEmpty() || vet.getLastName().isEmpty()) {
            validations.add("Missing First/Last name");
        }
        if (vet.getPhoneNumber().isEmpty()) {
            validations.add("Missing Phone Number");
        }

        Pattern phonePattern = Pattern.compile("\\d+(\\.\\d+)?");

        if (!vet.getPhoneNumber().isEmpty() && !phonePattern.matcher(vet.getPhoneNumber()).matches()){
            validations.add("Invalid Phone Number");
        }

        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

        if (!vet.getEmailId().isEmpty() && !emailPattern.matcher(vet.getEmailId()).matches()){
            validations.add("Invalid Email Id");
        }

        return validations;
    }

}
