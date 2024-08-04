package br.dev.diisk.application.dtos.expense_category;

import java.math.BigDecimal;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddExpenseCategoryRequest {
    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String name;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal budgetLimit;
}
