package br.dev.diisk.application.dtos.expense_category;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryExpensesDetailsDTO {
    private String categoryName;
    private BigDecimal amount;
    private BigDecimal budget;
    private BigDecimal percentageUsed;
}
