package com.planner.payments.service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.planner.payments.domain.PersonSession;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(PersonServiceImpl.PersonDetails details, Long refreshTokenExpiryTimestamp);
    boolean isJWTExpired(DecodedJWT decodedJWT);
    JwtClaimsRecord getClaims(String token);
    boolean isTokenValid(String token, String username);
}
