package br.dev.diisk.application.dtos.transaction_category;

import java.math.BigDecimal;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateTransactionCategoryRequest {

    private String name;

    @Positive(message = GlobalMessages.POSITIVE_VALUE)
    private BigDecimal budgetLimit;
}
