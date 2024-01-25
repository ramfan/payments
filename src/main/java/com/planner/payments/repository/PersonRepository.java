package com.planner.payments.repository;

import com.planner.payments.domain.Person;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
//    @NotNull
//    @Query("from Person join fetch Credit" )
//    Optional<Person> findById(@NotNull Long id);
}
