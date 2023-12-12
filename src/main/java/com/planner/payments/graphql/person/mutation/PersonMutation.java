package com.planner.payments.graphql.person.mutation;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;


@Controller
public class PersonMutation {

    private final PersonService personService;

    public PersonMutation(PersonService personService) {
        this.personService = personService;
    }

    @MutationMapping
    public PersonDTO addPerson(@Argument(name = "full_name") String fullName) {
        return personService.addPerson(new PersonDTO(fullName));
    }
}
