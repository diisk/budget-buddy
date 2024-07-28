package br.dev.diisk.infra.dtos.income;

import java.math.BigDecimal;

import br.dev.diisk.domain.entities.income.Income;
import lombok.Getter;

@Getter
public class CreateIncomeResponse {
    private Long id;
    private String description;
    private String categoryName;
    private BigDecimal value;
    private String date;

    public CreateIncomeResponse(Income income) {
        this.id = income.getId();
        this.description = income.getDescription();
        this.categoryName = income.getCategory().getName();
        this.value = income.getValue();
        this.date = income.getDate().toString();
    }
}
