package com.example.demo.service;

import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.models.owner.Owner;
import com.example.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetMapper petMapper;

    @Override
    public Optional<Owner> getById(Integer ownerId) {
        if (ownerId == null) {
            return Optional.empty();
        }
        return ownerRepository.findById(ownerId);
    }

    @Override
    public Owner getByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public List<Owner> getByName(String firstName, String lastName) {
        return null;
    }
}
