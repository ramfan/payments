package com.planner.payments.repository;

import com.planner.payments.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    @Query("SELECT u FROM Person u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Person> findPersonAndRolesByUsername(@Param("username") String username);
}
