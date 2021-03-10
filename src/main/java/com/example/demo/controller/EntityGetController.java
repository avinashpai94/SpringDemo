package com.example.demo.controller;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.service.OwnerServiceImpl;
import com.example.demo.service.PetServiceImpl;
import com.example.demo.service.VetServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/get")
public class EntityGetController {

    @Autowired
    OwnerServiceImpl ownerService;

    @Autowired
    VetServiceImpl vetService;

    @Autowired
    PetServiceImpl petService;

    @GetMapping("/owner")
    public ResponseEntity<String> getOwnerDetails(@RequestParam(required = false) Integer id, @RequestParam(required = false) String phone_number) throws JsonProcessingException {
        if (id == null && phone_number.isBlank()) {
            return new ResponseEntity<>("Missing ID/Phone number", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id != null) {
            return ownerService.getById(id);
        } else {
            return ownerService.getByPhoneNumber(phone_number);
        }

    }

    @GetMapping("/vet")
    public ResponseEntity<String> getVetDetails(@RequestParam(required = false) Integer id, @RequestParam(required = false) String phone_number) throws JsonProcessingException {
        if (id == null && phone_number.isBlank()) {
            return new ResponseEntity<>("Missing ID/Phone number", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (id != null) {
            return vetService.getById(id);
        } else {
            return vetService.getByPhoneNumber(phone_number);
        }

    }

    @GetMapping("/pet")
    public ResponseEntity<String> getPetDetails(@RequestParam(required = false) Integer petId, @RequestParam(required = false) Integer ownerId, @RequestParam(required = false) String phoneNumber) throws JsonProcessingException {
        if (petId == null && ownerId == null && phoneNumber.isEmpty()) {
            return new ResponseEntity<>("Missing ID/Owner ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (petId != null) {
            return petService.getById(petId);
        } else {
            OwnerDto ownerDto = new OwnerDto();
            ownerDto.setId(ownerId);
            ownerDto.setPhoneNumber(phoneNumber);
            return petService.getByOwner(ownerDto);
        }

    }

}

