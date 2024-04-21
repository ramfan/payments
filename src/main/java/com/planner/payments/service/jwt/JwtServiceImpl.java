package com.planner.payments.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtServiceImpl implements JwtService{
    private static String USER_ID_CLAIM_KEY = "user_id";
    private static String USER_ROLES_CLAIM_KEY = "user_roles";

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateToken(PersonServiceImpl.PersonDetails details) {

        List<String> roleList = details.getAuthorities()
                .stream()
                .map(role -> role.getAuthority()).toList();

        return JWT.create()
                .withClaim(USER_ID_CLAIM_KEY, details.getId())
                .withArrayClaim(USER_ROLES_CLAIM_KEY, roleList.toArray(String[]::new))
                .withSubject(details.getUsername())
                .withExpiresAt(Instant.now().plus(20, ChronoUnit.MINUTES))
                .sign(getSigningAlgorithm());
    }

    @Override
    public boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    public DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    @Override
    public JwtClaimsRecord getClaims(String token) {
        var decodedJWT = decodeToken(token);
        var id =  decodedJWT.getClaim(USER_ID_CLAIM_KEY).asLong();
        var roles = decodedJWT.getClaim(USER_ROLES_CLAIM_KEY).asArray(String.class);
        return new JwtClaimsRecord(id, roles, decodedJWT.getSubject());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        var decodedJWT = decodeToken(token);
        var username = decodedJWT.getSubject();
        return username.equals(userDetails.getUsername()) && !isJWTExpired(decodedJWT);
    }

}
