package com.example.demo.service;

import com.example.demo.controller.requests.AppointmentSignUpRequest;
import com.example.demo.dto.mapper.AppointmentMapper;
import com.example.demo.dto.mapper.PetMapper;
import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.appointment.AppointmentList;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.OwnerRepository;
import com.example.demo.repository.PetRepository;
import com.example.demo.utils.exception.ExceptionClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.utils.exception.ExceptionType;
import com.example.demo.utils.exception.EntityType;

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

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseEntity<String> getById(Integer appointmentId) throws JsonProcessingException {
        Optional<Appointment> appointment = appointmentRepository.findAppointmentById(appointmentId);
        if (appointment.isEmpty()) {
            String missingAppointment = "No Appointment with ID: ";
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                    missingAppointment +appointmentId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointment.get().toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getByVetId(VetDto vetDto) throws JsonProcessingException {
        List<Appointment> appointments = appointmentRepository.findAppointmentByVetId(vetDto.getId());
        if (appointments.isEmpty()) {
            String missingVetAppointment = "No Appointment Against Vet with ID: ";
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                    missingVetAppointment +vetDto.getId()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getByPetId(PetDto petDto) throws JsonProcessingException {
        List<Appointment> appointments = appointmentRepository.findAppointmentByPetId(petDto.getId());
        if (appointments.isEmpty()) {
            String missingPetAppointment = "No Appointment Against Pet with ID: ";
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                    missingPetAppointment +petDto.getId()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointments.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getByOwnerId(OwnerDto ownerDto) throws JsonProcessingException {

        List<Appointment> appointments = new ArrayList<Appointment>();
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerDto.getId());
        String missingOwnerAppointment = "No Appointment Against Owner with ID: ";
        if (ownerOptional.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                    missingOwnerAppointment +ownerDto.getId()), HttpStatus.NOT_FOUND);
        }

        Owner owner = ownerOptional.get();
        owner.setPets(petRepository.findByOwnerId(owner.getId()));
        if (owner.getPets().isEmpty()) {
            String missingPets = "No pets assigned to owner with ID: ";
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.PET,
                    missingPets +ownerDto.getId()), HttpStatus.NOT_FOUND);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        for (Pet pet : owner.getPets()) {
            PetDto petDto = petMapper.toPetDto(pet);
            AppointmentList appointmentList = objectMapper.readValue(getByPetId(petDto).getBody(), AppointmentList.class);
            appointments.addAll(appointmentList.getAppointmentList());

        }
        if (!appointments.isEmpty()) {
            return new ResponseEntity<>(appointments.toString(), HttpStatus.OK);
        }
        //throw exception
        return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                missingOwnerAppointment +ownerDto.getId()), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> getByTime(String startTime, String endTime) throws JsonProcessingException {
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByTimeSlot(startTime, endTime);
        if (!appointmentList.isEmpty()) {
            return new ResponseEntity<>(appointmentList.toString(), HttpStatus.OK);
        }
        String message = String.format("No appointment found between: %s and %s", startTime, endTime);
        return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                message), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> bookAppointment(String appointmentRequest) throws JsonProcessingException {
        AppointmentSignUpRequest appointmentSignUpRequest = objectMapper.readValue(appointmentRequest, AppointmentSignUpRequest.class);
        AppointmentDto appointmentDto = AppointmentMapper.toAppointmentDto(appointmentSignUpRequest.toAppointment());
        String validationError = "Appointment Description or Time Slot missing";
        String timeslotOccupiedError = "Appointment could not be booked because slot already occupied";
        if (!validateAppointment(appointmentDto)) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.VALIDATION_ERROR, EntityType.APPOINTMENT,
                    validationError), HttpStatus.NOT_FOUND);
        }

        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByMembers(appointmentDto.getVetId(),
                                                                                            appointmentDto.getPetId(),
                                                                                            appointmentDto.getOwnerId());
        /*
        * Raise timeslot is already occupied error if timeslots clash.
        * Can be ignored if the clashing appointment has been cancelled.
        * */
        if (!appointmentList.isEmpty()) {
            for (Appointment appointment : appointmentList){
                if (appointment.getTimeSlot().equals(appointmentDto.getTimeSlot()) && !appointment.isCanceled()) {
                    return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.DUPLICATE_ENTITY, EntityType.APPOINTMENT,
                            timeslotOccupiedError), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        //map dto to object and save in table
        Appointment appointment = new Appointment(appointmentDto);
        appointmentRepository.save(appointment);

        return new ResponseEntity<>(appointment.toString(), HttpStatus.OK);

    }


    public ResponseEntity<String> cancelAppointment (Integer appointmentId) throws JsonProcessingException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findAppointmentById(appointmentId);
        Appointment appointment;
        String message = String.format("No Appointment with ID: %s", appointmentId.toString());
        if (optionalAppointment.isEmpty()) {
            return new ResponseEntity<String>(ExceptionClass.toJSONString(ExceptionType.ENTITY_NOT_FOUND, EntityType.APPOINTMENT,
                    message), HttpStatus.NOT_FOUND);
        } else {
            appointmentRepository.cancelAppointment(appointmentId);
            appointment = optionalAppointment.get();
            appointment.setCanceled(true);
        }

        return new ResponseEntity<>(appointment.toString(), HttpStatus.OK);
    }

    private boolean validateAppointment(AppointmentDto appointmentDto) {
        return !appointmentDto.getDescription().isEmpty() && !appointmentDto.getTimeSlot().isEmpty();
    }
}
