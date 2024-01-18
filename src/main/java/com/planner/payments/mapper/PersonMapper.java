package com.planner.payments.mapper;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.domain.Person;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @InheritInverseConfiguration
    PersonDTO toPersonDto(Person person, @Context PersonCycleReferencesResolver cycleContext);

    Person toPerson(PersonDTO personDTO, @Context PersonCycleReferencesResolver cycleContext);
}
