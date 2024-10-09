package com.planner.payments;

import com.planner.payments.DTO.PersonDTO;
import static org.junit.jupiter.api.Assertions.*;

import com.planner.payments.constants.Role;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.repository.PersonRepository;
import com.planner.payments.service.PersonService.PersonService;
import com.planner.payments.service.PersonService.PersonServiceImpl;
import com.planner.payments.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@ActiveProfiles("dev")
@Testcontainers
public class JwtServiceTests {
    @Autowired
    JwtService jwtService;

    static String TEST_USER_NAME = "Test user";
    static Long EXPECTED_PERSON_ID = 1L;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Container
    static PostgreSQLContainer<?> container = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("example")
            .withExposedPorts(5432);


    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.sql.init.mode", () -> "always");
        registry.add("spring.jpa.properties.hibernate.enable_lazy_load_no_trans", () -> true);

    }

    @BeforeEach
    void initUser() throws NotFoundException {
        if(personRepository.findAll().isEmpty()){
            var personDto = new PersonDTO(TEST_USER_NAME);
            personDto.setUsername(TEST_USER_NAME);
            personDto.setPassword("password");
            personService.addPerson(personDto);
        }

    }

    @Test
    void generateJwtToken() throws NotFoundException {
        var person = personService.getPersonById(EXPECTED_PERSON_ID);
        var token = jwtService.generateToken(new PersonServiceImpl.PersonDetails(person), 1000L);
        assertFalse(token.isEmpty(), "Token can not be empty");
    }

    @Test
    void extractJwtTokenData() throws NotFoundException {
        var person = personService.getPersonById(EXPECTED_PERSON_ID);
        var token = jwtService.generateToken(new PersonServiceImpl.PersonDetails(person), 1000L);
        var jwtClaimsRecord = jwtService.getClaims(token);
        assertEquals(EXPECTED_PERSON_ID, jwtClaimsRecord.id());
        assertEquals(1, jwtClaimsRecord.roleList().length);
        assertEquals(Role.ADMIN.toString(), jwtClaimsRecord.roleList()[0]);
        assertEquals(TEST_USER_NAME, jwtClaimsRecord.username());
    }
}
