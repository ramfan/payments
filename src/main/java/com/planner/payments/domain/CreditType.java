package com.planner.payments.domain;

import com.planner.payments.constants.LoanType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class CreditType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
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
}
