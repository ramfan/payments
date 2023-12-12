package com.planner.payments.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("credit_set")
    private Set<CreditDTO> creditSet;

    public PersonDTO(String fullName) {
        this.fullName = fullName;
    }

}
