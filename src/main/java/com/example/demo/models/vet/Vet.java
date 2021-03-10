package com.example.demo.models.vet;

import com.example.demo.dto.models.OwnerDto;
import com.example.demo.dto.models.VetDto;
import com.example.demo.models.Person;
import com.example.demo.models.appointment.Appointment;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "vets")
public class Vet extends Person {

    @JsonProperty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private List<Speciality> specialties;

    @JsonProperty
    @OneToMany
    @JoinTable(name = "appointments", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
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

    protected List<Speciality> getSpecialtiesInternal() {
        if (this.specialties == null) {
            this.specialties = new ArrayList<>();
        }
        return this.specialties;
    }

    protected void setSpecialtiesInternal(List<Speciality> specialties) {
        this.specialties = specialties;
    }

    @XmlElement
    public List<Speciality> getSpecialties() {
        List<Speciality> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    public int getNrOfSpecialties() {
        return getSpecialtiesInternal().size();
    }

    public void addSpecialty(Speciality speciality) {
        getSpecialtiesInternal().add(speciality);
    }

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public Vet(VetDto vetDto) {
        this.setFirstName(vetDto.getFirstName());
        this.setLastName(vetDto.getLastName());
        this.setAppointments(vetDto.getAppointments());
        this.setSpecialties(vetDto.getSpecialties());
        this.setEmailId(vetDto.getEmailId());
        this.setPhoneNumber(vetDto.getPhoneNumber());
        this.setCreatedAt(DateUtils.DbDateTimeString());
    }

}