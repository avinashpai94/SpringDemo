package com.example.demo.controller;

import com.example.demo.controller.requests.AppointmentSignUpRequest;
import com.example.demo.dto.mapper.AppointmentMapper;
import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.service.AppointmentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentServiceImpl appointmentService;

    @GetMapping("/test/{id}")
    ResponseEntity<String> hello(@PathVariable Integer id) {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<String> getAppointmentDetailsByOwner(@PathVariable Integer id, HttpServletRequest request) {

        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Appointment> appointmentList = appointmentService.getByOwnerId(new OwnerDto().setId(id));
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<String>("Element Not Found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(appointmentList.toString(), HttpStatus.OK);
        }

    }

    @GetMapping("/vet/{id}")
    public ResponseEntity<String> getAppointmentDetailsByPet(@PathVariable Integer id, HttpServletRequest request) {

        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Appointment> appointmentList = appointmentService.getByVetId(new VetDto().setId(id));
        if (!appointmentList.isEmpty()) {
            return new ResponseEntity<String>("Error while searching for Appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<String>(appointmentList.toString(), HttpStatus.OK);
        }

    }

    @GetMapping( "/pet/{id}")
    public ResponseEntity<String> getAppointmentDetailsByVet(@PathVariable Integer id, HttpServletRequest request) {
        if (id == null) {
            return new ResponseEntity<String>("Invalid ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<Appointment> appointmentList = new ArrayList<>(appointmentService.getByPetId(new PetDto().setId(id)));
        if (appointmentList.isEmpty()) {
            return new ResponseEntity<String>("Element Not Found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>(appointmentList.toString(), HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewAppointment (@RequestBody String requestBody) throws JsonProcessingException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        AppointmentSignUpRequest appointmentSignUpRequest = objectMapper.readValue(requestBody, AppointmentSignUpRequest.class);
        AppointmentDto appointmentDto = AppointmentMapper.toAppointmentDto(appointmentSignUpRequest.toAppointment());
        Appointment appointment = appointmentService.bookAppointment(appointmentDto);

        if (appointment == null) {
            return new ResponseEntity<String>("Could not be booked", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<String>(appointment.toString(), HttpStatus.OK);
        }

    }

}
