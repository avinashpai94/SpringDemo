package com.example.demo.models.pet;

import com.example.demo.models.NamedEntity;
import com.example.demo.models.appointment.Appointment;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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

    @JsonProperty
    @OneToMany
    @JoinTable(name = "appointments", joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Appointment> appointments = new ArrayList<>();

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
