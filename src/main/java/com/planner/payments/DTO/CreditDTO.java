package com.planner.payments.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditDTO {
    private Long id;
    private String name;
    private Long creditSize;
    private Float percent;
    private LocalDate startDate;
    private Long monthsCount;
    private PersonDTO borrower;
    private CreditTypeDTO creditType;
}
