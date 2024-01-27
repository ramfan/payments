package com.planner.payments.DTO;

import com.planner.payments.constants.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private String name;
    private Long creditSize;
    private Float percent;
    private LocalDate startDate;
    private Long monthsCount;
    private PersonDTO borrower;
    private LoanType creditType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditDTO creditDTO = (CreditDTO) o;
        return Objects.equals(id, creditDTO.id) && Objects.equals(name, creditDTO.name) && Objects.equals(creditSize, creditDTO.creditSize) && Objects.equals(percent, creditDTO.percent) && Objects.equals(startDate, creditDTO.startDate) && Objects.equals(monthsCount, creditDTO.monthsCount) && Objects.equals(borrower, creditDTO.borrower) && creditType == creditDTO.creditType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creditSize, percent, startDate, monthsCount, borrower, creditType);
    }
}
