package com.example.demo.service;

import com.example.demo.dto.models.OwnerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface OwnerService {

    ResponseEntity<String> getById(Integer ownerId) throws JsonProcessingException;

    ResponseEntity<String> getByPhoneNumber(String phoneNumber) throws JsonProcessingException;

    ResponseEntity<String> createNewOwner(OwnerDto ownerDto) throws JsonProcessingException;

}
