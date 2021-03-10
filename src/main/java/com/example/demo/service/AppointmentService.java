package com.example.demo.service;


import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface AppointmentService {

    ResponseEntity<String> getById(Integer appointmentId) throws JsonProcessingException;

    ResponseEntity<String> getByVetId(VetDto vetDto) throws JsonProcessingException;

    ResponseEntity<String> getByPetId(PetDto petDto) throws JsonProcessingException;

    ResponseEntity<String> getByOwnerId(OwnerDto ownerDto) throws JsonProcessingException;

    ResponseEntity<String> getByTime(String startTime, String endTime) throws JsonProcessingException;

    ResponseEntity<String> bookAppointment(String appointmentRequest) throws JsonProcessingException;

    ResponseEntity<String> cancelAppointment(Integer appointmentId) throws JsonProcessingException;

}
