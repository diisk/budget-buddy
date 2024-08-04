package br.dev.diisk.application.dtos.income_category;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryIncomesDetailsDTO {
    private String categoryName;
    private BigDecimal amount;
}
