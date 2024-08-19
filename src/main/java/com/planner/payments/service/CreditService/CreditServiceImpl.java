package com.planner.payments.service.CreditService;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.mapper.CycleReferencesResolver;
import com.planner.payments.mapper.credit.CreditCycleReferencesResolver;
import com.planner.payments.mapper.credit.CreditMapper;
import com.planner.payments.repository.CreditRepository;
import com.planner.payments.repository.CreditTypeRepository;
import com.planner.payments.service.PersonService.PersonService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditTypeRepository creditTypeRepository;
    private final PersonService personService;
    private final CreditMapper creditMapper;

    private final CycleReferencesResolver creditCycleReferencesResolver;

    public CreditServiceImpl(CreditRepository creditRepository, CreditTypeRepository creditTypeRepository, PersonService personService, CreditMapper creditMapper, CycleReferencesResolver creditCycleReferencesResolver) {
        this.creditRepository = creditRepository;
        this.creditTypeRepository = creditTypeRepository;
        this.personService = personService;
        this.creditMapper = creditMapper;
        this.creditCycleReferencesResolver = creditCycleReferencesResolver;
    }

    @Override
    @Transactional
    public CreditDTO addCreditByUser(CreditDTO creditDTO) throws NotFoundException {
        var borrowerId = personService.getSelfInfo().getId();
        var borrower = personService.getPersonById(borrowerId);
        var loanType = creditTypeRepository.getByType(creditDTO.getCreditType()).orElseThrow(NotFoundException::new);
        var credit = creditMapper.toCredit(creditDTO, creditCycleReferencesResolver);
        credit.setCreditType(loanType);
        credit.setLoanBalance(credit.getCreditSize());
        credit.setBorrower(borrower);
        credit = creditRepository.save(credit);
        borrower.addCredit(credit);
        return creditMapper.toCreditDto(credit, creditCycleReferencesResolver);
    }

    @Override
    @Transactional
    public Long removeCreditById(Long id) {
        creditRepository.deleteById(id);
        return id;
    }

    @Override
    public CreditDTO getCreditById(Long id) throws NotFoundException {
        var credit = creditRepository.findById(id).orElseThrow(NotFoundException::new);
        return creditMapper.toCreditDto(credit, creditCycleReferencesResolver);
    }
}
