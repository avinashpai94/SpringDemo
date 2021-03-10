package com.example.demo.controller.requests;

import com.example.demo.models.vet.Vet;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetSignUpRequest extends SignUpRequest {

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private List<Integer> specialities;

    public Vet toVet() {
        Vet vet = new Vet();
        vet.setFirstName(this.getFirstName());
        vet.setLastName(this.getLastName());
        vet.setPhoneNumber(this.getPhoneNumber());
        vet.setEmailId(this.getEmailId());
        vet.setCreatedAt(DateUtils.DbDateTimeString());
        return vet;
    }

}
