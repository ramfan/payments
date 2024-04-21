package com.planner.payments.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(PersonServiceImpl.PersonDetails details);
    boolean isJWTExpired(DecodedJWT decodedJWT);
//    DecodedJWT decodeToken(String token);
    JwtClaimsRecord getClaims(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
