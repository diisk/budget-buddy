package br.dev.diisk.application.dtos.income;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateIncomeRequest {
    private String description;
    private Long categoryId;
    private Long resourceId;
    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal value;
    private LocalDateTime date;

}
