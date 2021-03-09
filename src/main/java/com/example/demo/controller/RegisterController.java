package com.example.demo.controller;

import com.example.demo.controller.requests.OwnerSignUpRequest;
import com.example.demo.controller.requests.PetSignUpRequest;
import com.example.demo.controller.requests.VetSignUpRequest;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.vet.Vet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/api/register")
class RegisterController {

    private final String OK_RESPONSE = "OK_RESPONSE";

    @Autowired
    private final AppointmentRepository appointmentRepository;

    public RegisterController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping("/owner")
    public ResponseEntity<String> registerOwner(@Valid OwnerSignUpRequest ownerSignUpRequest) {
        Owner owner = ownerSignUpRequest.toOwner();
        //perform validation using ownerService
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @PostMapping("/pet")
    public ResponseEntity<String> registerPet(@Valid PetSignUpRequest petSignUpRequest) {
        Pet pet = petSignUpRequest.toPet();
        //perform validation using pet service
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @PostMapping("/vet")
    public ResponseEntity<String> registerVet(@Valid VetSignUpRequest vetSignUpRequest) {
        Vet vet = vetSignUpRequest.toVet();
        //perform validation using vet service
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @GetMapping("/test/{id}")
    ResponseEntity<String> hello(@PathVariable Integer id) {
//        Optional<Appointment> appointment = appointmentRepository.findById(id);
//        return appointment.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK)).orElse(null);
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
