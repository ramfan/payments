package com.planner.payments.graphql.person.mutation;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.exception.UserNotFoundException;
import com.planner.payments.service.PersonService.PersonService;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import com.planner.payments.service.jwt.JwtService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.net.http.HttpResponse;


@Controller
public class PersonMutation {

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public PersonMutation(PersonService personService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
                        @Argument(name = "password") String password
    ) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var user = personService.loadUserByUsername(username);
        if(user != null && passwordEncoder.matches(password, user.getPassword()) ){
            return jwtService.generateToken((PersonServiceImpl.PersonDetails) user);
        }

        throw new UserNotFoundException();
    }

}
