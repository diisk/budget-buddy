package br.dev.diisk.application.dtos.expense;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ExpenseResponse {
    private Long id;
    private String description;
    private String categoryName;
    private String resourceName;
    private BigDecimal value;
    private String date;

}
