package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class LoanPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long value;

    @ManyToOne
    private Credit loan;

    @ManyToOne
    private LoanPaymentType paymentType;

    public LoanPayment(Long value, Credit loan, LoanPaymentType paymentType) {
        this.value = value;
        this.loan = loan;
        this.paymentType = paymentType;
    }
}
