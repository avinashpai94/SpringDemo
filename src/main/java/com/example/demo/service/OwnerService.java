package com.example.demo.service;

import com.example.demo.models.owner.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {

    Optional<Owner> getById(Integer ownerId);

    Owner getByPhoneNumber(String phoneNumber);

    List<Owner> getByName(String firstName, String lastName);

}
