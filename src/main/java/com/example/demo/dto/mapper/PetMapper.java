package com.example.demo.dto.mapper;

import com.example.demo.dto.models.PetDto;
import com.example.demo.models.pet.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {
    public static PetDto toPetDto(Pet pet) {
        return new PetDto()
                .setId(pet.getId())
                .setName(pet.getName())
                .setOwnerId(pet.getOwnerId())
                .setBirthDate(pet.getBirthDate().toString())
                .setType(pet.getType())
                .setAppointments(pet.getAppointments());
    }
}
