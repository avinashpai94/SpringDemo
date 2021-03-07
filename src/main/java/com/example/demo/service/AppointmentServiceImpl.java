package com.example.demo.service;

import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetMapper petMapper;

    @Override
    public Appointment getById(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);
        return appointment;
    }

    @Override
    public List<Appointment> getByVet(VetDto vetDto) {
        List<Appointment> appointments = appointmentRepository.findAppointmentByVetId(vetDto.getId());
        if (!appointments.isEmpty()) {
            return appointments;
        }
        //throw exception
        return null;
    }

    @Override
    public List<Appointment> getByPetId(PetDto petDto) {
        List<Appointment> appointments = appointmentRepository.findAppointmentByPetId(petDto.getId());
        if (!appointments.isEmpty()) {
            return appointments;
        }
        //throw exception
        return null;
    }

    @Override
    public List<Appointment> getByOwnerId(OwnerDto ownerDto) {

        List<Appointment> appointments = new ArrayList<Appointment>();
        Owner owner = ownerRepository.findByPhoneNumber(ownerDto.getPhoneNumber());

        for (Pet pet : ownerDto.getPets()) {
            PetDto petDto = petMapper.toPetDto(pet);
            appointments.addAll(getByPetId(petDto));
        }

        if (!appointments.isEmpty()) {
            return appointments;
        }
        //throw exception
        return null;
    }

    @Override
    public List<Appointment> getByTime(String startTime, String endTime) {
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByTimeSlot(startTime, endTime);
        if (!appointmentList.isEmpty()) {
            return appointmentList;
        }
        return null;
    }

    @Override
    public Appointment bookAppointment(AppointmentDto appointmentDto) {
        return null;
    }
}
