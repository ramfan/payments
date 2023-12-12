package com.planner.payments.service.PersonService;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.domain.Person;
import com.planner.payments.exception.NotFoundException;


public interface PersonService {
    PersonDTO addPerson(PersonDTO personDTO);
    PersonDTO getPersonDtoById(Long id) throws NotFoundException;
    Person getPersonById(Long id) throws NotFoundException;

    Person save(Person person);
}
