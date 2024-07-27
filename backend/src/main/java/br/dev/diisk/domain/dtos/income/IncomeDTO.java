package br.dev.diisk.domain.dtos.income;

import java.math.BigDecimal;

import br.dev.diisk.domain.entities.income.Income;
import lombok.Getter;

@Getter
public class IncomeDTO {
    private Long id;
    private String description;
    private String categoryName;
    private BigDecimal value;
    private String date;

    public IncomeDTO(Income income) {
        this.id = income.getId();
        this.description = income.getDescription();
        this.categoryName = income.getCategory().getName();
        this.value = income.getValue();
        this.date = income.getDate().toString();
    }
}
