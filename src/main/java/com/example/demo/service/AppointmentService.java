package com.example.demo.service;


import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.PetDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.appointment.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment getById(Integer appointmentId);

    List<Appointment> getByVetId(VetDto vetDto);

    List<Appointment> getByPetId(PetDto petDto);

    List<Appointment> getByOwnerId(OwnerDto ownerDto);

    List<Appointment> getByTime(String startTime, String endTime);

    Appointment bookAppointment(AppointmentDto appointmentDto);

}
