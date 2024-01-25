package com.planner.payments.mapper;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.constants.LoanType;
import com.planner.payments.domain.CreditType;
import com.planner.payments.domain.Person;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

//    @InheritInverseConfiguration
    public abstract PersonDTO toPersonDto(Person person, @Context PersonCycleReferencesResolver cycleContext);

    public abstract Person toPerson(PersonDTO personDTO, @Context PersonCycleReferencesResolver cycleContext);

    public LoanType map(CreditType creditType) {
        return creditType.getType();
    }

    public CreditType map(LoanType creditType){
        var creditTypeEntity = new CreditType();
        creditTypeEntity.setType(creditType);
        return creditTypeEntity;
    }
}
