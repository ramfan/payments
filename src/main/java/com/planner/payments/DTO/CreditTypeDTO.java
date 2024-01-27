package com.planner.payments.DTO;

import com.planner.payments.constants.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditTypeDTO {
    private Long id;
    private LoanType type;

    public CreditTypeDTO(LoanType type) {
        this.type = type;
    }
}
