package com.example.demo.models.owner;

import com.example.demo.dto.models.AppointmentDto;
import com.example.demo.dto.models.OwnerDto;
import com.example.demo.models.Person;
import com.example.demo.models.pet.Pet;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @JsonProperty
    @Column(name = "address")
    private String address;

    @OneToMany
    @JoinTable(name = "pets", joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Pet> pets;

    public Owner() {

    }

    protected List<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new ArrayList<>();
        }

        return this.pets;
    }

    protected void setPetsInternal(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
    }

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            getPetsInternal().add(pet);
        }
        pet.setOwnerId(this.getId());
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("telephone", this.getPhoneNumber())
                .append("email", this.getEmailId())
                .append("address", this.getAddress())
                .toString();
    }

    public String toJSONString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public Owner(OwnerDto ownerDto) {
        this.setFirstName(ownerDto.getFirstName());
        this.setLastName(ownerDto.getLastName());
        this.setPets(ownerDto.getPets());
        this.setAddress(ownerDto.getAddress());
        this.setEmailId(ownerDto.getEmailId());
        this.setPhoneNumber(ownerDto.getPhoneNumber());
        this.setCreatedAt(DateUtils.DbDateTimeString());
    }


}
