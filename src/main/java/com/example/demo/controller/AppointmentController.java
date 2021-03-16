package com.example.demo.controller;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.service.AppointmentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/appointment")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentService;

    @GetMapping("")
    public ResponseEntity<String> getAppointmentDetailsById(@RequestParam(required = false) Integer id, HttpServletRequest request) throws JsonProcessingException {
        return appointmentService.getById(id);
    }

    @GetMapping("/owner")
    public ResponseEntity<String> getAppointmentDetailsByOwner(@RequestParam(required = false) Integer id, HttpServletRequest request) throws JsonProcessingException {
        return appointmentService.getByOwnerId(new OwnerDto().setId(id));
    }

    @GetMapping("/vet")
    public ResponseEntity<String> getAppointmentDetailsByVet(@RequestParam(required = false) Integer id, HttpServletRequest request) throws JsonProcessingException {
        return appointmentService.getByVetId(new VetDto().setId(id));
    }

    @GetMapping( "/pet")
    public ResponseEntity<String> getAppointmentDetailsByPet(@RequestParam(required = false) Integer id, HttpServletRequest request) throws JsonProcessingException {
        return appointmentService.getByPetId(new PetDto().setId(id));
    }

    @GetMapping( "/time")
    public ResponseEntity<String> getAppointmentDetailsByTime(@RequestParam(required = false) String start_time, @RequestParam(required = false) String end_time) throws JsonProcessingException {
        return appointmentService.getByTime(start_time, end_time);
    }

    @PostMapping("/book")
    public ResponseEntity<String> createNewAppointment (@RequestBody String requestBody) throws JsonProcessingException {
        return appointmentService.bookAppointment(requestBody);
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestParam(required = true) Integer id, HttpServletRequest request) throws JsonProcessingException {
        return appointmentService.cancelAppointment(id);
    }

}
