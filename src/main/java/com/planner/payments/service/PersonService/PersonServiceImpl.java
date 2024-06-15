package com.planner.payments.service.PersonService;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.constants.Role;
import com.planner.payments.domain.Person;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.mapper.CycleReferencesResolver;
import com.planner.payments.mapper.person.PersonMapper;
import com.planner.payments.repository.PersonRepository;
import com.planner.payments.service.RoleService.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final CycleReferencesResolver personCycleReferencesResolver;

    private final RoleService roleService;


    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, CycleReferencesResolver personCycleReferencesResolver, RoleService roleService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personCycleReferencesResolver = personCycleReferencesResolver;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public PersonDTO addPerson(PersonDTO personDTO) throws NotFoundException {
        var newPerson = personMapper.toPerson(personDTO, personCycleReferencesResolver);
        var isEmptyPersonList = personRepository.findAll().isEmpty();

        if (newPerson != null) {
            newPerson.setEnabled(true);

            if(isEmptyPersonList){
                var adminRole = roleService.getRoleByName(Role.ADMIN);
                newPerson.addRole(adminRole);
            } else {
                var userRole = roleService.getRoleByName(Role.USER);
                newPerson.addRole(userRole);
            }

            var createdPerson = save(newPerson);
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
        return personMapper.toPersonDto(getPersonById(id), personCycleReferencesResolver);
    }

    @Override
    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public void flush() {
        personRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = personRepository.findPersonAndRolesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new PersonDetails(person);
    }

    public static class PersonDetails implements UserDetails {

        private final Person person;

        public PersonDetails(Person person) {
            this.person = person;
        }

        public Long getId(){
            return this.person.getId();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return person.getRoles();
        }

        @Override
        public String getPassword() {
            return this.person.getPassword();
        }

        @Override
        public String getUsername() {
            return this.person.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return this.person.getEnabled();
        }

        public Person getPerson() {
            return person;
        }
    }
}
