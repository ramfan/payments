package com.planner.payments;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.DTO.RoleDTO;
import com.planner.payments.service.jwt.JwtService;
import org.springframework.security.test.context.support.WithMockUser;
import com.planner.payments.constants.LoanType;
import com.planner.payments.constants.Operation;
import com.planner.payments.constants.Role;
import com.planner.payments.domain.Credit;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.repository.CreditRepository;
import com.planner.payments.repository.PersonRepository;
import com.planner.payments.repository.RoleRepository;
import com.planner.payments.service.PersonService.PersonService;
import jakarta.annotation.PreDestroy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@Testcontainers
public class PaymentsApplicationTestContainersTests extends ApplicationContextProvider {

    static String TEST_USER_NAME = "Test user";
    static Long EXPECTED_PERSON_ID = 1L;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    PersonService personService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtService jwtService;

    static HttpGraphQlTester graphQlTester;

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

    @PreDestroy
    public void stop() {
        container.stop();
    }

    @BeforeEach
    void initGQLTester() {
        WebTestClient client =
                MockMvcWebTestClient.bindToApplicationContext((WebApplicationContext) this.applicationContext)
                        .configureClient()
                        .baseUrl("/graphql")
                        .build();
        graphQlTester = HttpGraphQlTester.create(client);

    }

    @BeforeEach
    void initUser() throws NotFoundException {
        if(personRepository.findAll().isEmpty()){
            var personDto = new PersonDTO(TEST_USER_NAME);
            personDto.setUsername("username");
            personDto.setPassword("password");
            personService.addPerson(personDto);
        }

    }

    void removeAllCredits(){
        creditRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities={"ADMIN"})
    void addingPersonTest() {
        var result = graphQlTester.documentName("registration")
                .variable("fullName", "Test username")
                .variable("username", "username1")
                .variable("password", "ps")
                .execute().path("registration").entity(PersonDTO.class).get();

        assertInstanceOf(PersonDTO.class, result);
        assertEquals("Test username", result.getFullName());
        assertTrue( result.getRoles().stream().anyMatch((RoleDTO r) -> r.getName().equals(Role.USER.toString())));
        assertTrue(result.getCreditSet().isEmpty());

    }

    @Test
    void userRoleOperationsTest() throws NotFoundException {
        var role = roleRepository.getRoleByName(Role.USER.toString()).orElseThrow(NotFoundException::new);
        var operations = role.getAllowedOperations().stream().map(o -> Operation.valueOf(o.getAuthority())).collect(Collectors.toSet());
        assertNotNull(role);
        assertFalse(operations.isEmpty());

    }

    @Test
    void adminRoleOperationsTest() throws NotFoundException {
        var role = roleRepository.getRoleByName(Role.ADMIN.toString()).orElseThrow(NotFoundException::new);
        var operations = role.getAllowedOperations().stream().map(o -> Operation.valueOf(o.getAuthority())).collect(Collectors.toSet());
        assertNotNull(role);
        assertFalse(operations.isEmpty());

    }

//    @Test
    @WithMockUser(authorities={"ADMIN"})
    void getPersonByIdTest() throws NotFoundException {
        var testPerson = personRepository.findById(EXPECTED_PERSON_ID).orElseThrow(NotFoundException::new);

        var getPersonByIdResult = graphQlTester.documentName("getPersonById")
                .variable("id",testPerson.getId())
                .execute().path("getPersonById").entity(PersonDTO.class).get();

        assertInstanceOf(PersonDTO.class, getPersonByIdResult);
        assertEquals(EXPECTED_PERSON_ID, getPersonByIdResult.getId());
        assertEquals("Test user", getPersonByIdResult.getFullName());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"}, username = "username")
    void addFirstCredit(){

        var addCreditRequestResult = graphQlTester.documentName("addCredit")
                .variable("percent", 14.9)
                .variable("credit_size", 4600000)
                .variable("credit_type", LoanType.MORTGAGE.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .execute().path("addCredit").entity(CreditDTO.class).get();

        var person = addCreditRequestResult.getBorrower();
        assertInstanceOf(CreditDTO.class, addCreditRequestResult);
        assertEquals(242, addCreditRequestResult.getMonthsCount());
        assertEquals(LoanType.MORTGAGE, addCreditRequestResult.getCreditType());
        assertInstanceOf(PersonDTO.class, person);
        assertEquals(TEST_USER_NAME, person.getFullName());
        assertEquals(4600000, addCreditRequestResult.getCreditSize());
    }

    @Test
    @WithMockUser(authorities={"ADMIN"}, username = "username")
    void addSecondCredit() throws NotFoundException {
        removeAllCredits();
        graphQlTester.documentName("addCredit")
                .variable("percent", 14.9)
                .variable("credit_size", 4600000)
                .variable("credit_type", LoanType.MORTGAGE.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .execute().path("addCredit").entity(CreditDTO.class).get();

      graphQlTester.documentName("addCredit")
                .variable("percent", 24.9)
                .variable("credit_size", 374000)
                .variable("credit_type", LoanType.CONSUMER_LOAN.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .execute().path("addCredit").entity(CreditDTO.class).get();

        var person = personRepository.findById(EXPECTED_PERSON_ID).orElseThrow(NotFoundException::new);
        var creditSet = person.getCreditSet();
        assertEquals(2, creditSet.size());
        assertEquals(TEST_USER_NAME, person.getFullName());
    }

    @Test
    @WithMockUser(authorities={"USER"}, username = "username")
    void removeCredit() throws NotFoundException {
        removeAllCredits();
        var removeCandidate = graphQlTester.documentName("addCredit")
                .variable("percent", 14.9)
                .variable("credit_size", 4600000)
                .variable("credit_type", LoanType.MORTGAGE.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .execute().path("addCredit").entity(CreditDTO.class).get();

        graphQlTester.documentName("addCredit")
                .variable("percent", 24.9)
                .variable("credit_size", 374000)
                .variable("credit_type", LoanType.CONSUMER_LOAN.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .execute().path("addCredit").entity(CreditDTO.class).get();

        var person = personRepository.findById(EXPECTED_PERSON_ID).orElseThrow(NotFoundException::new);
        var creditSet = person.getCreditSet();
        assertEquals(2, creditSet.size());
        assertEquals(TEST_USER_NAME, person.getFullName());

        var removedId = graphQlTester.documentName("removeCredit")
                .variable("id", removeCandidate.getId())
                .execute().path("removeCredit").entity(Long.class).get();

        creditSet = personRepository.findById(EXPECTED_PERSON_ID).get().getCreditSet();

        assertThrows(NotFoundException.class, () -> creditRepository.findById(removedId).orElseThrow(NotFoundException::new));
        assertTrue(creditSet.stream().noneMatch((Credit credit) -> credit.getId().equals(removedId)));

    }

    @Test
    @WithMockUser(authorities={"USER"}, username = "username")
    void getSelfData() {

        var getPersonByIdResult = graphQlTester.documentName("getSelfData")
                .execute().path("getSelfData").entity(PersonDTO.class).get();

        assertInstanceOf(PersonDTO.class, getPersonByIdResult);
        assertEquals(EXPECTED_PERSON_ID, getPersonByIdResult.getId());
        assertEquals("Test user", getPersonByIdResult.getFullName());
    }

    @Test
    @WithMockUser(authorities={"USER"}, username = "username")
    void addCreditWithName(){

        var addCreditRequestResult = graphQlTester.documentName("addCreditWithName")
                .variable("percent", 14.9)
                .variable("credit_size", 4600000)
                .variable("credit_type", LoanType.MORTGAGE.toString())
                .variable("months_count", 242)
                .variable("start_date", "2023-11-09")
                .variable("name", "Mortgage")
                .execute().path("addCredit").entity(CreditDTO.class).get();

        var person = addCreditRequestResult.getBorrower();
        assertInstanceOf(CreditDTO.class, addCreditRequestResult);
        assertEquals(242, addCreditRequestResult.getMonthsCount());
        assertEquals(LoanType.MORTGAGE, addCreditRequestResult.getCreditType());
        assertInstanceOf(PersonDTO.class, person);
        assertEquals(TEST_USER_NAME, person.getFullName());
        assertEquals("Mortgage", addCreditRequestResult.getName());
        assertEquals(4600000, addCreditRequestResult.getCreditSize());
    }
}

