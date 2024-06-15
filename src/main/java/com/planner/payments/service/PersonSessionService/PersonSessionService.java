package com.planner.payments.service.PersonSessionService;

import com.planner.payments.domain.Person;
import com.planner.payments.domain.PersonSession;
import com.planner.payments.exception.NotFoundException;

import java.util.UUID;

public interface PersonSessionService {
    PersonSession createPersonSession(Person person);
    Boolean isExpired(UUID refreshToken) throws NotFoundException, IllegalAccessException;
    Boolean isExpired(PersonSession personSession) throws IllegalAccessException;
    PersonSession refreshSession(UUID refreshToken) throws NotFoundException;
}
