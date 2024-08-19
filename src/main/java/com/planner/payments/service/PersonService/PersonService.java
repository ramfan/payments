package com.planner.payments.service.PersonService;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.domain.Person;
import com.planner.payments.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public interface PersonService extends UserDetailsService {
    PersonDTO addPerson(PersonDTO personDTO) throws NotFoundException;
    PersonDTO getPersonDtoById(Long id) throws NotFoundException;
    PersonDTO getSelfInfo() throws NotFoundException;
    Person getPersonById(Long id) throws NotFoundException;
    void flush();
    Person save(Person person);
}
