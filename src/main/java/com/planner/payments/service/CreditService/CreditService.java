package com.planner.payments.service.CreditService;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.exception.NotFoundException;

public interface CreditService {
    CreditDTO addCreditByUser(Long borrowerId, CreditDTO creditDTO) throws NotFoundException;
}
