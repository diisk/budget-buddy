package br.dev.diisk.application.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SavingGoalDTO {
    private String goalName;
    private BigDecimal targetValue;
    private BigDecimal currentValue;
    private BigDecimal percentageAchieved;
}
