package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
//@EqualsAndHashCode
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name" )
    @Setter
    private String fullName;

    @Column(nullable = false, unique = true)
    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    @Column(nullable = false, unique = true)
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Collection<Role> roles = new HashSet<>();

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "borrower", orphanRemoval = true, fetch = FetchType.LAZY)
    private final Collection<Credit> creditSet = new HashSet<>();

    public Person(String fullName) {
        this.fullName = fullName;
    }

    public Person(){}

    public void addCredit(Credit credit) {
        creditSet.add(credit);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(fullName, person.fullName) && Objects.equals(username, person.username) && Objects.equals(password, person.password) && Objects.equals(enabled, person.enabled) && Objects.equals(roles, person.roles) && Objects.equals(creditSet, person.creditSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, username, password, enabled, roles, creditSet);
    }
}
