package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class LoanPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    private Long value;

    @ManyToOne
    @Getter
    private Credit loan;

    @ManyToOne
    @Getter
    private LoanPaymentType paymentType;

    public LoanPayment(Long value, Credit loan, LoanPaymentType paymentType) {
        this.value = value;
        this.loan = loan;
        this.paymentType = paymentType;
    }
}
