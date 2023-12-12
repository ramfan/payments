package com.planner.payments.service.PersonService;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.domain.Person;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.mapper.CreditCycleReferencesResolver;
import com.planner.payments.mapper.PersonCycleReferencesResolver;
import com.planner.payments.mapper.PersonMapper;
import com.planner.payments.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final  PersonCycleReferencesResolver personCycleReferencesResolver;


    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, PersonCycleReferencesResolver personCycleReferencesResolver) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personCycleReferencesResolver = personCycleReferencesResolver;
    }

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        Person newPerson = personMapper.toPerson(personDTO, personCycleReferencesResolver);

        if(newPerson != null){
            var createdPerson =  save(newPerson);
            return personMapper.toPersonDto(createdPerson, personCycleReferencesResolver);
        }

        return null;
    }

    @Override
    public Person getPersonById(Long id) throws NotFoundException {
        return personRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public PersonDTO getPersonDtoById(Long id) throws NotFoundException {
        return personMapper.toPersonDto(getPersonById(id),personCycleReferencesResolver);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
