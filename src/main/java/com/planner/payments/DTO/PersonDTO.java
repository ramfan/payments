package com.planner.payments.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    @Builder.Default
    private Collection<CreditDTO> creditSet = new HashSet<>();
    @Builder.Default
    private Collection<RoleDTO> roles = new HashSet<>();

    public PersonDTO(String fullName) {
        this.fullName = fullName;
        this.creditSet = new HashSet<>();
        this.roles = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) && Objects.equals(fullName, personDTO.fullName) && Objects.equals(username, personDTO.username) && Objects.equals(password, personDTO.password) && Objects.equals(creditSet, personDTO.creditSet) && Objects.equals(roles, personDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, username, password, creditSet, roles);
    }
}
