package com.example.demo.service;

import com.example.demo.dto.models.VetDto;
import com.example.demo.models.vet.Speciality;
import com.example.demo.models.vet.Vet;

import java.util.List;

public interface VetService {

    Vet getById(Integer vetId);

    Vet getByPhoneNumber(String phoneNumber);

    List<Vet> getByName(String firstName, String lastName);

    List<Speciality> getVetSpeciality(VetDto vetDto);

}
