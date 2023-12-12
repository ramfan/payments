package com.planner.payments.service.CreditService;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.mapper.CreditCycleReferencesResolver;
import com.planner.payments.mapper.CreditMapper;
import com.planner.payments.repository.CreditRepository;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreditServiceImpl implements CreditService{
    private final CreditRepository creditRepository;
    private final PersonService personService;
    private final CreditMapper creditMapper;

    private final CreditCycleReferencesResolver creditCycleReferencesResolver;

    public CreditServiceImpl(CreditRepository creditRepository, PersonService personService, CreditMapper creditMapper, CreditCycleReferencesResolver creditCycleReferencesResolver) {
        this.creditRepository = creditRepository;
        this.personService = personService;
        this.creditMapper = creditMapper;
        this.creditCycleReferencesResolver = creditCycleReferencesResolver;
    }

    @Override
    @Transactional
    public CreditDTO addCreditByUser(Long borrowerId, CreditDTO creditDTO) throws NotFoundException {
        var borrower = personService.getPersonById(borrowerId);
        var credit = creditMapper.toCredit(creditDTO, creditCycleReferencesResolver);
        credit.setBorrower(borrower);
        credit = creditRepository.save(credit);
        borrower.addCredit(credit);
        personService.save(borrower);
        return creditMapper.toCreditDto(credit, creditCycleReferencesResolver);
    }
}
