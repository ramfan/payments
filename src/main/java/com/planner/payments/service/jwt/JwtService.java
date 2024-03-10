package com.planner.payments.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.planner.payments.service.PersonService.PersonServiceImpl;

public interface JwtService {
    String generateToken(PersonServiceImpl.PersonDetails details);
    boolean isJWTExpired(DecodedJWT decodedJWT);
}
