package com.planner.payments.graphql.person.query;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Set;

@Controller
public class PersonQuery {

    private final PersonService personService;

    public PersonQuery(PersonService personService) {
        this.personService = personService;
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PersonDTO getPersonById (@Argument Long id) throws NotFoundException {
        return personService.getPersonDtoById(id);
    }

    @SchemaMapping
    public Collection<CreditDTO> creditSet(PersonDTO personDTO) {
        return personDTO.getCreditSet();
    }
}
