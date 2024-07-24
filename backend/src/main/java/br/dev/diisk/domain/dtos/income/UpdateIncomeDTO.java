package br.dev.diisk.domain.dtos.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateIncomeDTO {
    private String description;
    private Long categoryId;
    private BigDecimal value;
    private LocalDateTime date;

}
