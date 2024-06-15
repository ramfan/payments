package com.planner.payments.graphql.person.mutation;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.exception.UserNotFoundException;
import com.planner.payments.service.PersonService.PersonService;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import com.planner.payments.service.PersonSessionService.PersonSessionService;
import com.planner.payments.service.jwt.JwtService;
import graphql.GraphQLContext;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;


@Controller
public class PersonMutation {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PersonSessionService personSessionService;


    public PersonMutation(PersonService personService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, PersonSessionService personSessionService) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.personSessionService = personSessionService;
    }

    @MutationMapping
    public PersonDTO registration(
            @Argument(name = "full_name") String fullName,
            @Argument(name = "username") String username,
            @Argument(name = "password") String password
    ) throws NotFoundException {
        return personService.addPerson(PersonDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .build()
        );
    }

    @MutationMapping
    public String login(@Argument(name = "username") String username,
                                        @Argument(name = "password") String password, GraphQLContext context
    ) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var user = (PersonServiceImpl.PersonDetails)personService.loadUserByUsername(username);
        var session = personSessionService.createPersonSession(user.getPerson());

        if(passwordEncoder.matches(password, user.getPassword()) ){
            context.put("refresh_token", session.getRefreshToken().toString());
            context.put("refresh_token_expiry", session.getExpiresIn());

            return jwtService.generateToken(user, session.getExpiresIn());
        }

        throw new UserNotFoundException();
    }

    @MutationMapping
    public String refreshSession(GraphQLContext context) throws NotFoundException {
        Map<String, HttpCookie> cookie = context.get("cookie");
        var refreshToken = cookie.get("refresh_token").getValue();
        var session = personSessionService.refreshSession(UUID.fromString(refreshToken));
        var user = (PersonServiceImpl.PersonDetails) personService.loadUserByUsername(session.getPerson().getUsername());
        context.put("refresh_token", session.getRefreshToken().toString());
        context.put("refresh_token_expiry", session.getExpiresIn());

        return jwtService.generateToken(user, session.getExpiresIn());
    }

}
