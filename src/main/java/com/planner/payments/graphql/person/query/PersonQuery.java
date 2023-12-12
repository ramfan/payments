package com.planner.payments.graphql.person.query;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class PersonQuery {

    private final PersonService personService;

    public PersonQuery(PersonService personService) {
        this.personService = personService;
    }

    @QueryMapping
    public PersonDTO getPersonById (@Argument Long id) throws NotFoundException {
        return personService.getPersonDtoById(id);
    }

    @SchemaMapping
    public Set<CreditDTO> creditSet(PersonDTO personDTO) {
        return personDTO.getCreditSet();
    }
}
