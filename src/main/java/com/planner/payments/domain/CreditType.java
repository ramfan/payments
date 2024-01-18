package com.planner.payments.domain;

import com.planner.payments.constants.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class CreditType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private LoanType type;

//    @OneToMany(mappedBy = "creditType")
//    @Getter
//    private Set<Credit> creditSet;

    public CreditType(LoanType type) {
        this.type = type;
//        this.creditSet = creditSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditType creditType = (CreditType) o;
        return Objects.equals(id, creditType.id) && type == creditType.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
