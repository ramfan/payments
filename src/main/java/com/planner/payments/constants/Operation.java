package com.planner.payments.constants;

public enum Operation {
    WRITE("WRITE"),

    READ("READ"),
    USER_MANAGING("USER_MANAGING");

    private final String value;
    Operation(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }
}
