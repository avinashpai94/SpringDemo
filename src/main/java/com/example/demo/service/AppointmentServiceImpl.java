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
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static com.example.demo.utils.exception.AppException.throwException;
import static com.example.demo.utils.exception.EntityType.APPOINTMENT;
import static com.example.demo.utils.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.example.demo.utils.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.example.demo.utils.exception.ExceptionType.VALIDATION_ERROR;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetMapper petMapper;

    @Override
    public Appointment getById(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findAppointmentById(appointmentId);
        return appointment.orElse(null);
    }

    @Override
    public List<Appointment> getByVetId(VetDto vetDto) {
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
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerDto.getId());
        if (ownerOptional.isEmpty()) {
            //throw exception
            return new ArrayList<Appointment>();
        }

        Owner owner = ownerOptional.get();
        owner.setPets(petRepository.findByOwnerId(owner.getId()));
        if (owner.getPets().isEmpty()) {
            //throw exception
            return new ArrayList<Appointment>();
        }

        for (Pet pet : owner.getPets()) {
            PetDto petDto = petMapper.toPetDto(pet);
            appointments.addAll(getByPetId(petDto));
        }
        if (!appointments.isEmpty()) {
            return appointments;
        }
        //throw exception
        return new ArrayList<Appointment>();
    }

    @Override
    public List<Appointment> getByTime(String startTime, String endTime) {
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByTimeSlot(startTime, endTime);
        if (!appointmentList.isEmpty()) {
            return appointmentList;
        }
        //throw exception
        return new ArrayList<>();
    }

    @Override
    public Appointment bookAppointment(AppointmentDto appointmentDto) {

        if (!validate(appointmentDto)) {
            //throw exception
            return null;
        }

        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByMembers(appointmentDto.getVetId(),
                                                                                            appointmentDto.getPetId(),
                                                                                            appointmentDto.getOwnerId());
        //Check if timeslots coincide
        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList){
                if (appointment.getTimeSlot().equals(appointmentDto.getTimeSlot())) {
                    //throw exception
                    return null;
                }
            }
        }

        //map dto to object and save in table
        Appointment appointment = new Appointment(appointmentDto);
        appointmentRepository.save(appointment);

        return appointment;

    }

    private boolean validate(AppointmentDto appointmentDto) {
        return !appointmentDto.getDescription().isEmpty() && !appointmentDto.getTimeSlot().isEmpty();
    }
}
