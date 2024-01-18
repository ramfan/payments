package com.planner.payments.repository;

import com.planner.payments.constants.LoanType;
import com.planner.payments.domain.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
    Optional<CreditType> getByType(LoanType loanType);
}
