package com.example.demo.dto.models;

import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.vet.Speciality;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String emailId;

    private List<Speciality> specialties;

    private List<Appointment> appointments;
}
