package com.example.demo.models.appointment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AppointmentList {

    @JsonProperty
    List<Appointment> appointmentList;
}
