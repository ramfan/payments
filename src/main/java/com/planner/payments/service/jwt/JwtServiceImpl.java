package com.planner.payments.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateToken(PersonServiceImpl.PersonDetails details) {

        return JWT.create()
                .withClaim("user_id", details.getId())
                .withArrayClaim("user_roles", (String[]) details.getAuthorities().toArray())
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .sign(getSigningAlgorithm());
    }

    @Override
    public boolean isJWTExpired(DecodedJWT decodedJWT) {
        try {
            Date expiresAt = decodedJWT.getExpiresAt();
            return expiresAt.before(new Date());

        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
