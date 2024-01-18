package com.planner.payments.constants;

public enum PaymentType {
    BASIC("BASIC"),
    REDUCTION_OF_LOAN_TERM("REDUCTION_OF_LOAN_TERM"),
    PAYMENT_REDUCTION("PAYMENT_REDUCTION");

    private final String value;
    PaymentType(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
