package com.example.demo.dto.mapper;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.models.owner.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public static OwnerDto toOwnerDto (Owner owner) {
        return new OwnerDto()
                .setId(owner.getId())
                .setFirstName(owner.getFirstName())
                .setLastName(owner.getLastName())
                .setPhoneNumber(owner.getPhoneNumber())
                .setAddress(owner.getAddress())
                .setEmailId(owner.getEmailId())
                .setPets(owner.getPets());
    }
}
