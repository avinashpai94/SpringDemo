package com.example.demo.models.pet;

import com.example.demo.models.NamedEntity;
import com.example.demo.models.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

    @JsonProperty
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @JsonProperty
    @Column(name = "type")
    private Integer type;

    @JsonProperty
    @Column(name = "owner_id")
    private Integer ownerId;

    @Transient
    private List<Appointment> appointments = new ArrayList<>();

    protected List<Appointment> getAppointmentInternal() {
        if (this.appointments == null) {
            this.appointments = new ArrayList<>();
        }
        return this.appointments;
    }

    protected void setAppointmentInternal(Collection<Appointment> visits) {
        this.appointments = new ArrayList<>(visits);
    }

    public List<Appointment> getAppointment() {
        List<Appointment> sortedVisits = new ArrayList<>(getAppointmentInternal());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    public void setAppointment(Appointment appointment) {
        getAppointmentInternal().add(appointment);
        appointment.setPetId(this.getId());
    }



}
