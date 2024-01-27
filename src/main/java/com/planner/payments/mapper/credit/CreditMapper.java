package com.planner.payments.mapper.credit;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.constants.LoanType;
import com.planner.payments.domain.Credit;
import com.planner.payments.domain.CreditType;
import com.planner.payments.mapper.CycleReferencesResolver;
import com.planner.payments.mapper.creditType.CreditTypeMapper;
import com.planner.payments.mapper.person.PersonMapper;
import com.planner.payments.mapper.role.RoleMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CreditTypeMapper.class, RoleMapper.class})
public abstract class CreditMapper {
    @Mapping(target = "borrower.creditSet", ignore = true)
    public abstract CreditDTO toCreditDto(Credit credit, @Context CycleReferencesResolver cycleContext);
    @Mapping(target = "borrower.creditSet", ignore = true)
    public abstract Credit toCredit(CreditDTO creditDTO, @Context CycleReferencesResolver cycleContext);
}
