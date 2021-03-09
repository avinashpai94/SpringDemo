package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity {

    @JsonProperty
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @JsonProperty
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @JsonProperty
    @Column(name = "phone_number")
    @NotEmpty
    private String phoneNumber;

    @JsonProperty
    @Column(name = "email_id")
    private String emailId;

}
