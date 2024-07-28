package br.dev.diisk.application.dtos.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateIncomeRequest {
    private String description;
    private Long categoryId;
    private BigDecimal value;
    private LocalDateTime date;

}
