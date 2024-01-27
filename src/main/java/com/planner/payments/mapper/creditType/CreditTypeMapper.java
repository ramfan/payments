package com.planner.payments.mapper.creditType;

import com.planner.payments.constants.LoanType;
import com.planner.payments.domain.CreditType;
import com.planner.payments.mapper.credit.CreditMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CreditTypeMapper {

    public String creditTypeToString(CreditType creditType) {
        return creditType.getType().toString();
    }

    public CreditType creditTypeToEnum(String creditType){
        var creditTypeEntity = new CreditType();
        creditTypeEntity.setType(LoanType.valueOf(creditType));
        return creditTypeEntity;
    }
}
