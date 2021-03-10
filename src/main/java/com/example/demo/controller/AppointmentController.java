package com.example.demo.controller;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.service.AppointmentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentService;

    @GetMapping("/owner/{id}")
    public ResponseEntity<String> getAppointmentDetailsByOwner(@PathVariable Integer id, HttpServletRequest request) throws JsonProcessingException {

        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return appointmentService.getByOwnerId(new OwnerDto().setId(id));

    }

    @GetMapping("/vet/{id}")
    public ResponseEntity<String> getAppointmentDetailsByPet(@PathVariable Integer id, HttpServletRequest request) throws JsonProcessingException {

        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return appointmentService.getByVetId(new VetDto().setId(id));

    }

    @GetMapping( "/pet/{id}")
    public ResponseEntity<String> getAppointmentDetailsByVet(@PathVariable Integer id, HttpServletRequest request) throws JsonProcessingException {
        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return appointmentService.getByPetId(new PetDto().setId(id));
    }

    @GetMapping( "/time")
    public ResponseEntity<String> getAppointmentDetailsByTime(@RequestParam(required = false) String start_time, @RequestParam(required = false) String end_time) throws JsonProcessingException {
        if (start_time == null || end_time == null) {
            return new ResponseEntity<String>("Missing Start/End Times", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return appointmentService.getByTime(start_time, end_time);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewAppointment (@RequestBody String requestBody) throws JsonProcessingException {
        return appointmentService.bookAppointment(requestBody);

    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Integer id, HttpServletRequest request) throws JsonProcessingException {
        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return appointmentService.cancelAppointment(id);
    }

}
