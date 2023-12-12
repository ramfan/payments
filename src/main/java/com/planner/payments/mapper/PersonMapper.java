package com.planner.payments.mapper;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.domain.Person;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO toPersonDto(Person person, @Context PersonCycleReferencesResolver cycleContext);
    Person toPerson(PersonDTO personDTO, @Context PersonCycleReferencesResolver cycleContext);
}
