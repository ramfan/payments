package com.planner.payments.repository;

import com.planner.payments.domain.PersonSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonSessionRepository extends JpaRepository<PersonSession, String> {
    Optional<PersonSession> findByRefreshToken(UUID refreshToken);
}
