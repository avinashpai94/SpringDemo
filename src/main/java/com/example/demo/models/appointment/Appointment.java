package com.example.demo.models.appointment;

import com.example.demo.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @Column(name = "vet_id")
    private Integer vetId;

    @Column(name = "pet_id")
    private Integer petId;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "timeslot")
    private String timeSlot;

    @Column(name = "description")
    private String description;

    @Column(name = "canceled", columnDefinition= "boolean default 'false'")
    private boolean canceled;

    public Appointment() {
        this.setCreatedAt(ZonedDateTime.now(ZoneId.of("America/Phoenix"))
                                        .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss")));
    }

}