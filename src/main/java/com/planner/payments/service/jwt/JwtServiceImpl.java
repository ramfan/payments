package com.planner.payments.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.planner.payments.service.PersonService.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;


@Component
public class JwtServiceImpl implements JwtService{
    private static String USER_ID_CLAIM_KEY = "user_id";
    private static String USER_ROLES_CLAIM_KEY = "user_roles";
    private static String USER_REFRESH_TOKEN_EXPIRY_KEY = "refresh_token_expiry";

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public String generateToken(PersonServiceImpl.PersonDetails details, Long refreshTokenExpiryTimestamp) {

        List<String> roleList = details.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return JWT.create()
                .withClaim(USER_ID_CLAIM_KEY, details.getId())
                .withArrayClaim(USER_ROLES_CLAIM_KEY, roleList.toArray(String[]::new))
                .withClaim(USER_REFRESH_TOKEN_EXPIRY_KEY, refreshTokenExpiryTimestamp)
                .withSubject(details.getUsername())
                .withExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
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
    public boolean isTokenValid(String token, String userName) {
        var decodedJWT = decodeToken(token);
        var username = decodedJWT.getSubject();
        return username.equals(userName) && !isJWTExpired(decodedJWT);
    }

}
