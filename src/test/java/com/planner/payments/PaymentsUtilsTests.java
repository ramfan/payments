package com.planner.payments;


import com.planner.payments.utils.PaymentUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentsUtilsTests {

    @Test
    void getMonthlyPaymentTest() {
        Double loanBalance = 4600000D;
        Double percent = 14.90D;
        Long monthCount = 242L;
        assertEquals(60232.71D, PaymentUtils.getMonthlyPaymentMortgage(loanBalance, percent, monthCount, LocalDate.of(2023, 11, 9)));
    }

    @Test
    void getScheduledPayments() {
        Double loanBalance = 4600000D;
        Double percent = 14.90D;
        Long monthCount = 242L;

        var paymentsList = PaymentUtils.getMortgagePaymentSchedule(loanBalance, percent, monthCount, LocalDate.of(2023, 11, 2));
        assertEquals(242, paymentsList.size());
    }
}
