package com.planner.payments.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentUtils {
    public static Double getMonthlyPayment(Double loanBalance, Double percent, Long monthCount) {
        double result;
        var monthlyPercent = getMonthlyPercent(percent);
        result = (monthlyPercent * Math.pow(1 + monthlyPercent, monthCount) / (Math.pow(1 + monthlyPercent, monthCount) - 1));

        return result * loanBalance;
    }

    public static Double getMonthlyPaymentMortgage(Double loanBalance, Double percent, Long monthCount, LocalDate contractSigning) {
        double result;
        var fullMonthPayment = monthCount;
        var monthlyPercent = getMonthlyPercent(percent);

        if (contractSigning.getDayOfMonth() != 1) {
            fullMonthPayment--;
        }

        result = monthlyPercent / ((1 - Math.pow(1 + monthlyPercent, -(fullMonthPayment - 1))));
        result *= loanBalance;


        return 1.0D * Math.round(result * 100) / 100;
    }

    public static List<Payment> getMortgagePaymentSchedule(Double loanBalance, Double percent, Long monthCount, LocalDate fromStart) {
        Double monthlyPayment = getMonthlyPaymentMortgage(loanBalance, percent, monthCount, fromStart);
        var loanBalanceWithOverpayment = loanBalance + getOverPayment(monthlyPayment, monthCount, loanBalance);
        return getPaymentSchedule(loanBalanceWithOverpayment, monthlyPayment, fromStart);
    }

    public static List<Payment> getPaymentSchedule(Double loanBalance, Double monthlyPayment, LocalDate start) {
        List<Payment> paymentList = new ArrayList<>();
        Double balance = loanBalance;
        LocalDate nextPaymentDate = start.plusMonths(1);
        var contractSigningDay = start.getDayOfMonth();

        if (contractSigningDay != 1) {
            var peerDayPayment = monthlyPayment / 30;
            var firstMonthPayment = monthlyPayment - peerDayPayment * contractSigningDay;
            paymentList.add(new Payment(nextPaymentDate, firstMonthPayment));
            balance -= firstMonthPayment;
        }

        while (balance > 0) {
            if (balance < monthlyPayment) {
                paymentList.add(new Payment(nextPaymentDate, balance));
            } else {
                nextPaymentDate = nextPaymentDate.plusMonths(1);
                paymentList.add(new Payment(nextPaymentDate, monthlyPayment));
            }

            balance -= monthlyPayment;
        }

        return paymentList;
    }

    private static double getMonthlyPercent(double percent) {
        return percent / 12 * 0.01d;
    }

    private static Double getOverPayment(Double monthlyPayment, Long monthCount, Double loanBalance) {
        return monthlyPayment * monthCount - loanBalance;
    }

    public record Payment(LocalDate date, Double payment) {
        public Payment(LocalDate date, Double payment) {
            this.payment = payment;
            this.date = date;
        }
    }

}
