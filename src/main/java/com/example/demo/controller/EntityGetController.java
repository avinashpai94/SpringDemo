package com.example.demo.controller;

import com.example.demo.service.OwnerServiceImpl;
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

}

