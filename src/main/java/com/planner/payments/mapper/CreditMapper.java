package com.planner.payments.mapper;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.constants.LoanType;
import com.planner.payments.domain.Credit;
import com.planner.payments.domain.CreditType;
import com.planner.payments.domain.Person;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CreditMapper {
    @Mapping(target = "borrower.creditSet", ignore = true)
    public abstract CreditDTO toCreditDto(Credit credit, @Context CreditCycleReferencesResolver cycleContext);
    @Mapping(target = "borrower.creditSet.borrower", ignore = true)
    public abstract Credit toCredit(CreditDTO creditDTO, @Context CreditCycleReferencesResolver cycleContext);

    public String creditTypeToString(CreditType creditType) {
        return creditType.getType().toString();
    }

    public CreditType creditTypeToEnum(String creditType){
        var creditTypeEntity = new CreditType();
        creditTypeEntity.setType(LoanType.valueOf(creditType));
        return creditTypeEntity;
    }
}
