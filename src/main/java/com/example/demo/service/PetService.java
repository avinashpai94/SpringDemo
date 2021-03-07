package com.example.demo.service;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.models.pet.Pet;

import java.util.List;

public interface PetService {

    List<Pet> getByOwner(OwnerDto ownerDto);

    Pet getById(Integer petId);

}
