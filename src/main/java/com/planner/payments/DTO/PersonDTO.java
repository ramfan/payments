package com.planner.payments.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String fullName;
    private Set<CreditDTO> creditSet;

    public PersonDTO(String fullName) {
        this.fullName = fullName;
    }

}
