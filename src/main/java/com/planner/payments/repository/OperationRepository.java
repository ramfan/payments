package com.planner.payments.repository;

import com.planner.payments.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Optional<Operation> getOperationByName(String name);
}
