package com.example.demo.controller;

import com.example.demo.controller.requests.OwnerSignUpRequest;
import com.example.demo.controller.requests.PetSignUpRequest;
import com.example.demo.controller.requests.VetSignUpRequest;
import com.example.demo.dto.mapper.OwnerMapper;
import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.mapper.VetMapper;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.vet.Speciality;
import com.example.demo.models.vet.SpecialtyList;
import com.example.demo.models.vet.Vet;
import com.example.demo.service.OwnerServiceImpl;
import com.example.demo.service.PetServiceImpl;
import com.example.demo.service.VetServiceImpl;
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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/register")
class RegisterController {

    private final String OK_RESPONSE = "OK_RESPONSE";

    @Autowired
    OwnerServiceImpl ownerService;

    @Autowired
    VetServiceImpl vetService;

    @Autowired
    PetServiceImpl petService;

    @PostMapping("/owner")
    public ResponseEntity<String> registerOwner(@RequestBody OwnerSignUpRequest ownerSignUpRequest) throws JsonProcessingException {
        OwnerDto ownerDto = OwnerMapper.toOwnerDto(ownerSignUpRequest.toOwner());
        return ownerService.createNewOwner(ownerDto);
    }

    @PostMapping("/pet")
    public ResponseEntity<String> registerPet(@RequestBody PetSignUpRequest petSignUpRequest) {
        PetDto petDto = PetMapper.toPetDto(petSignUpRequest.toPet());
        //perform validation using pet service
        return petService.createNewPet(petDto);
    }

    @PostMapping("/vet")
    public ResponseEntity<String> registerVet(@RequestBody VetSignUpRequest vetSignUpRequest) throws JsonProcessingException {
        VetDto vetDto = VetMapper.toVetDto(vetSignUpRequest.toVet());
        return vetService.createNewVet(vetDto);
    }

    @PostMapping("/specialties")
    public ResponseEntity<String> addVetSpecialties(@RequestParam(required = true) Integer id, @RequestBody(required = false) SpecialtyList specialityList) throws JsonProcessingException {
        return vetService.addVetSpecialities(id, specialityList);
    }


    @GetMapping("/test/{id}")
    ResponseEntity<String> hello(@PathVariable Integer id) {
//        Optional<Appointment> appointment = appointmentRepository.findById(id);
//        return appointment.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK)).orElse(null);
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
