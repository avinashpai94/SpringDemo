package com.example.demo.controller;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.service.OwnerServiceImpl;
import com.example.demo.service.PetServiceImpl;
import com.example.demo.service.VetServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/get")
@CrossOrigin(origins = "http://localhost:3000")
public class EntityGetController {

    @Autowired
    OwnerServiceImpl ownerService;

    @Autowired
    VetServiceImpl vetService;

    @Autowired
    PetServiceImpl petService;

    @GetMapping("/owner")
    public ResponseEntity<String> getOwnerDetails(@RequestParam(required = false) Integer id, @RequestParam(required = false) String phone_number) throws JsonProcessingException {
        if (id != null) {
            return ownerService.getById(id);
        } else {
            return ownerService.getByPhoneNumber(phone_number);
        }

    }

    @GetMapping("/vet")
    public ResponseEntity<String> getVetDetails(@RequestParam(required = false) Integer id, @RequestParam(required = false) String phone_number) throws JsonProcessingException {
        if (id != null) {
            return vetService.getById(id);
        } else {
            return vetService.getByPhoneNumber(phone_number);
        }
    }

    @GetMapping("/pet")
    public ResponseEntity<String> getPetDetails(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer owner_id, @RequestParam(required = false) String phone_number) throws JsonProcessingException {
        if (id != null) {
            return petService.getById(id);
        } else {
            OwnerDto ownerDto = new OwnerDto();
            ownerDto.setId(owner_id);
            ownerDto.setPhoneNumber(phone_number);
            return petService.getByOwner(ownerDto);
        }

    }

}

