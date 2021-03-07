package com.example.demo.dto.mapper;

import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.Pet;
import com.example.demo.models.vet.Vet;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDto toAppointmentDto (Appointment appointment, Vet vet, Owner owner, Pet pet) {
        return new AppointmentDto()
                .setId(appointment.getId())
                .setVetFirstName(vet.getFirstName())
                .setVetLastName(vet.getLastName())
                .setOwnerFirstName(owner.getFirstName())
                .setOwnerLastName(owner.getLastName())
                .setPetName(pet.getName())
                .setTimeSlot(appointment.getTimeSlot())
                .setDescription(appointment.getDescription())
                .setCancelled(appointment.isCanceled())
                .setCreatedAt(appointment.getCreatedAt());
    }

}
