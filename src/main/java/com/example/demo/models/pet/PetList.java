package com.example.demo.models.pet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PetList {

    @JsonProperty
    List<Pet> petList;
}
