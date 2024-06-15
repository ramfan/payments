package com.planner.payments.service.PersonSessionService;

import com.planner.payments.domain.Person;
import com.planner.payments.domain.PersonSession;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.repository.PersonSessionRepository;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class PersonSessionServiceImpl implements PersonSessionService{
    private final static Long EXPIRED_TIME = 2592000001L;

    private final PersonSessionRepository personSessionRepository;

    public PersonSessionServiceImpl(PersonSessionRepository personSessionRepository, PersonService personService) {
        this.personSessionRepository = personSessionRepository;
    }

    @Transactional
    @Override
    public PersonSession createPersonSession(Person person) {
        var newSession = new PersonSession();
        newSession.setPerson(person);
        newSession.setExpiresIn(EXPIRED_TIME);
        newSession.setRefreshToken(UUID.randomUUID());
        newSession.setCreatedAt(new Date());

        return personSessionRepository.save(newSession);
    }

    @Override
    public Boolean isExpired(UUID refreshToken) throws NotFoundException, IllegalAccessException {
        var personSession = personSessionRepository.findByRefreshToken(refreshToken).orElseThrow(NotFoundException::new);
        return isExpired(personSession);
    }

    @Override
    public Boolean isExpired(PersonSession personSession) throws IllegalAccessException {
        //небольшая проверка на вмешательство из вне
        if(personSession.getExpiresIn().compareTo(EXPIRED_TIME) != 0 ){
            throw new IllegalAccessException();
        }

        var now = new Date();
        var created = personSession.getCreatedAt();
        var diff = now.getTime() - created.getTime();

        return EXPIRED_TIME.compareTo(diff) < 1;
    }

    @Override
    @Transactional
    public PersonSession refreshSession(UUID refreshToken) throws NotFoundException {
        var personSession = personSessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(NotFoundException::new);

        var newPersonSession = createPersonSession(personSession.getPerson());
        personSessionRepository.delete(personSession);

        return newPersonSession;
    }
}
