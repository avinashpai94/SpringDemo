package com.example.demo.controller;

import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.vet.Vet;
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
    private final OwnerRepository ownerRepository;

    @Autowired
    private final PetRepository petRepository;

    @Autowired
    private final VetRepository vetRepository;

    public RegisterController(OwnerRepository ownerRepository, PetRepository petRepository, VetRepository vetRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;

    }

    @PostMapping("/owner")
    public ResponseEntity<String> registerOwner(@Valid Owner owner) {
        //perform validation using owner service
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @PostMapping("/pet")
    public ResponseEntity<String> registerPet(@Valid Pet pet) {
        //perform validation using pet service
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @PostMapping("/vet")
    public ResponseEntity<String> registerVet(@Valid Vet vet) {
        //perform validation using vet service
        return new ResponseEntity<String>(OK_RESPONSE, HttpStatus.OK);
    }

    @GetMapping("/test/{id}")
    ResponseEntity<String> hello(@PathVariable Integer id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.map(value -> new ResponseEntity<>(value.toString(), HttpStatus.OK)).orElse(null);
    }
}
