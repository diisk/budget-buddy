package br.dev.diisk.application.dtos.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateExpenseRequest {
    private String description;
    private Long categoryId;
    private Long resourceId;
    private BigDecimal value;
    private LocalDateTime date;

}
