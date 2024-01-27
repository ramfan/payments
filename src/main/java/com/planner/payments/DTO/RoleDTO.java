package com.planner.payments.DTO;

import com.planner.payments.constants.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    @Builder.Default
    private Collection<Operation> allowedOperations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(id, roleDTO.id) && Objects.equals(name, roleDTO.name) && Objects.equals(allowedOperations, roleDTO.allowedOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allowedOperations);
    }
}
