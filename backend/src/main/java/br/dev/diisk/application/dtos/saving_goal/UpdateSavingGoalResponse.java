package br.dev.diisk.application.dtos.saving_goal;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateSavingGoalResponse {

    private Long id;
    private String goalName;
    private BigDecimal targetValue;
    private String endDate;
    private String startDate;
    
}
