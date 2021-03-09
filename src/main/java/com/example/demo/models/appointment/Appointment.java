package com.example.demo.models.appointment;

import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.models.BaseEntity;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @JsonProperty
    @Column(name = "vet_id")
    private Integer vetId;

    @JsonProperty
    @Column(name = "pet_id")
    private Integer petId;

    @JsonProperty
    @Column(name = "owner_id")
    private Integer ownerId;

    @JsonProperty
    @Column(name = "timeslot")
    private String timeSlot;

    @JsonProperty
    @Column(name = "description")
    private String description;

    @JsonProperty
    @Column(name = "canceled", columnDefinition= "boolean default 'false'")
    private boolean canceled;

    public Appointment(AppointmentDto appointmentDto) {
        this.setVetId(appointmentDto.getVetId());
        this.setOwnerId(appointmentDto.getOwnerId());
        this.setPetId(appointmentDto.getPetId());
        this.setTimeSlot(appointmentDto.getTimeSlot());
        this.setDescription(appointmentDto.getDescription());
        this.setCanceled(false);
        this.setCreatedAt(DateUtils.DbDateTimeString());
    }

    public Appointment() {

    }

    @SneakyThrows
    public String toString () {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}