package com.example.demo.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDto {

    private Integer id;

    private String vetFirstName;

    private String vetLastName;

    private String ownerFirstName;

    private String ownerLastName;

    private String petName;

    private String timeSlot;

    private String description;

    private boolean cancelled;

    private String createdAt;
}
