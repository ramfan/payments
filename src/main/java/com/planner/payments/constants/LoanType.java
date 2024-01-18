package com.planner.payments.constants;

public enum LoanType {
    CONSUMER_LOAN("CONSUMER_LOAN"),
    MORTGAGE("MORTGAGE");
    private final String value;

    LoanType(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
