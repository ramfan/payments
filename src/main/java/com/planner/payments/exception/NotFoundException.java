package com.planner.payments.exception;

public class NotFoundException extends Exception{
    public NotFoundException(){
        super("Error: Not found");
    }
}
