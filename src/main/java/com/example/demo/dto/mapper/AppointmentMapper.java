package com.example.demo.dto.mapper;

import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.models.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public static AppointmentDto toAppointmentDto (Appointment appointment) {
        return new AppointmentDto()
                .setId(appointment.getId())
                .setVetId(appointment.getVetId())
                .setOwnerId(appointment.getOwnerId())
                .setPetId(appointment.getPetId())
                .setTimeSlot(appointment.getTimeSlot())
                .setDescription(appointment.getDescription())
                .setCancelled(appointment.isCanceled())
                .setCreatedAt(appointment.getCreatedAt());
    }

}
