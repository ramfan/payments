package com.planner.payments.DTO;

import com.planner.payments.constants.LoanType;
import lombok.Data;

@Data
public class CreditTypeDTO {
    private Long id;
    private LoanType type;

    public CreditTypeDTO(LoanType type) {
        this.type = type;
    }
}
