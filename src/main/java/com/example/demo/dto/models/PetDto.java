package com.example.demo.dto.models;

import com.example.demo.models.appointment.Appointment;
import com.example.demo.models.owner.Owner;
import com.example.demo.models.pet.PetType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDto {

    private Integer id;

    private String name;

    private String birthDate;

    private Integer type;

    private Integer ownerId;

    private List<Appointment> appointments;
}
