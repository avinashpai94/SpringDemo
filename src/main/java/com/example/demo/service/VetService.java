package com.example.demo.service;

import com.example.demo.dto.models.VetDto;
import com.example.demo.models.vet.Speciality;
import com.example.demo.models.vet.SpecialtyList;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VetService {

    ResponseEntity<String> getById(Integer vetId) throws JsonProcessingException;

    ResponseEntity<String> getByPhoneNumber(String phoneNumber) throws JsonProcessingException;

    ResponseEntity<String> createNewVet(VetDto vetDto) throws JsonProcessingException;

    ResponseEntity<String> addVetSpecialities(Integer vetId, SpecialtyList specialityList) throws JsonProcessingException;

}
