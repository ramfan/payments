package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "role_operation",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "operation_id") }
    )
    private final Set<Operation> allowedOperations = new HashSet<>();

    public Collection<Operation> getAllowedOperations() {
        return allowedOperations;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void addAllowedOperation(Operation operation) {
        allowedOperations.add(operation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(allowedOperations, role.allowedOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allowedOperations);
    }
}
