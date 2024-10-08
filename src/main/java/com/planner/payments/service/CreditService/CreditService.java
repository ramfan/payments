package com.planner.payments.service.CreditService;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface CreditService {
    @Transactional
    CreditDTO addCreditByUser(CreditDTO creditDTO) throws NotFoundException;
    @Transactional
    Long removeCreditById(Long id);
    CreditDTO getCreditById(Long id) throws NotFoundException;;
}
