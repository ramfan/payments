package com.planner.payments.domain;

import com.planner.payments.constants.PaymentType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class LoanPaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    private PaymentType type;

    public LoanPaymentType(PaymentType type) {
        this.type = type;
    }
}
