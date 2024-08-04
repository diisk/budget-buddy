package br.dev.diisk.application.dtos.expense_category;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryExpensesDetailsDTO {

    @JsonIgnore
    private Long id;
    
    private String categoryName;
    private BigDecimal value;
    private BigDecimal budget;
    private BigDecimal percentageUsed;
}
