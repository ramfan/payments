package com.planner.payments.mapper.person;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.DTO.RoleDTO;
import com.planner.payments.constants.LoanType;
import com.planner.payments.constants.Operation;
import com.planner.payments.domain.Credit;
import com.planner.payments.domain.CreditType;
import com.planner.payments.domain.Person;
import com.planner.payments.domain.Role;
import com.planner.payments.mapper.CycleReferencesResolver;
import com.planner.payments.mapper.credit.CreditMapper;
import com.planner.payments.mapper.creditType.CreditTypeMapper;
import com.planner.payments.mapper.role.RoleMapper;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CreditTypeMapper.class, RoleMapper.class})
public abstract class PersonMapper {

   @Mapping(target = "creditSet", qualifiedByName = "mapWithoutData")
    public abstract PersonDTO toPersonDto(Person person, @Context CycleReferencesResolver cycleContext);

    @Mapping(target = "creditSet", ignore = true)
    public abstract Person toPerson(PersonDTO personDTO, @Context CycleReferencesResolver cycleContext);

    @Named("mapWithoutData")
    @Mapping(target = "borrower.creditSet", ignore = true)
    public abstract CreditDTO mapWithoutData(Credit source);

    @Named("mapWithoutData")
    @Mapping(target = "borrower.creditSet", ignore = true)
    public abstract Credit mapWithoutData(CreditDTO source);
}
