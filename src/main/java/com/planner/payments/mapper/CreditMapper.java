package com.planner.payments.mapper;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.domain.Credit;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditDTO toCreditDto(Credit credit, @Context CreditCycleReferencesResolver cycleContext);
    Credit toCredit(CreditDTO creditDTO, @Context CreditCycleReferencesResolver cycleContext);
}
