package com.planner.payments.graphql.credit.mutation;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.CreditTypeDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.constants.LoanType;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.service.CreditService.CreditService;
import jakarta.annotation.Nullable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class CreditMutation {

    private final CreditService creditService;

    public CreditMutation(CreditService creditService) {
        this.creditService = creditService;
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public CreditDTO addCredit(
            @Argument(name = "credit_size") Long creditSize,
            @Argument(name = "percent") Float percent,
            @Argument(name = "start_date") LocalDate startDate,
            @Argument(name = "months_count") Long monthsCount,
            @Argument(name = "credit_type") LoanType creditType,
            @Nullable @Argument(name = "name") String name
            ) throws NotFoundException {
        var creditDto = new CreditDTO();
        creditDto.setName(name);
        creditDto.setCreditSize(creditSize);
        creditDto.setMonthsCount(monthsCount);
        creditDto.setStartDate(startDate);
        creditDto.setPercent(percent);
        creditDto.setCreditType(creditType);


        return creditService.addCreditByUser(creditDto);

    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Long removeCredit(@Argument(name = "id") Long id) {
        return creditService.removeCreditById(id);
    }


    @SchemaMapping(field = "borrower")
    public PersonDTO getBorrower(CreditDTO creditDTO) {
        return creditDTO.getBorrower();
    }
}
