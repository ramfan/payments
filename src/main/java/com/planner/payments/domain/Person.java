package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "borrower", orphanRemoval = true, fetch = FetchType.LAZY)
    private final Set<Credit> creditSet = new HashSet<>();

    public Person(String fullName) {
        this.fullName = fullName;
    }

    public Person(){}

    public void addCredit(Credit credit) {
        creditSet.add(credit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id) && fullName.equals(person.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }
}
