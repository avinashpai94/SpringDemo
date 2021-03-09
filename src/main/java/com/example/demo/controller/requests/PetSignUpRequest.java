package com.example.demo.controller.requests;

import com.example.demo.models.pet.Pet;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetSignUpRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String name;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String birthDate;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Integer type;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private Integer ownerId;

    public Pet toPet() {
        Pet pet = new Pet();
        pet.setName(this.getName());
        pet.setOwnerId(this.getOwnerId());
        pet.setType(this.getType());
        pet.setBirthDate(this.getBirthDate());
        pet.setCreatedAt(DateUtils.DbDateTimeString());
        return pet;
    }
}
