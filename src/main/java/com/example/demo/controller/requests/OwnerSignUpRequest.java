package com.example.demo.controller.requests;

import com.example.demo.models.owner.Owner;
import com.example.demo.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerSignUpRequest extends SignUpRequest {

    public Owner toOwner() {
        Owner owner = new Owner();
        owner.setFirstName(this.getFirstName());
        owner.setLastName(this.getLastName());
        owner.setPhoneNumber(this.getPhoneNumber());
        owner.setEmailId(this.getEmailId());
        owner.setAddress(this.getAddress());
        owner.setCreatedAt(DateUtils.DbDateTimeString());
        return owner;
    }

}
