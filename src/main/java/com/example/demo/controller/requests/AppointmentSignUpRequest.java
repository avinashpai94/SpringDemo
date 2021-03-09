package com.example.demo.controller.requests;


import com.example.demo.models.appointment.Appointment;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentSignUpRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Integer vetId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Integer ownerId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Integer petId;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String timeSlot;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String description;

    public Appointment toAppointment() {
        Appointment appointment = new Appointment();
        appointment.setVetId(this.getVetId());
        appointment.setOwnerId(this.getOwnerId());
        appointment.setPetId(this.getPetId());
        appointment.setTimeSlot(this.getTimeSlot());
        appointment.setDescription(this.getDescription());
        appointment.setCanceled(false);
        appointment.setCreatedAt(DateUtils.DbDateTimeString());
        return appointment;
    }
}
