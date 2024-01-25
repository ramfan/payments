package com.planner.payments.DTO;

import com.planner.payments.constants.LoanType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreditTypeDTO {
    private Long id;
    private LoanType type;

    public CreditTypeDTO(LoanType type) {
        this.type = type;
    }
}
