package com.example.demo.dto.mapper;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.vet.Vet;
import org.springframework.stereotype.Component;

@Component
public class VetMapper {
    public VetDto toVetDto(Vet vet) {
        return new VetDto()
                .setFirstName(vet.getFirstName())
                .setLastName(vet.getLastName())
                .setPhoneNumber(vet.getPhoneNumber())
                .setEmailId(vet.getEmailId())
                .setSpecialties(vet.getSpecialties());
    }
}
