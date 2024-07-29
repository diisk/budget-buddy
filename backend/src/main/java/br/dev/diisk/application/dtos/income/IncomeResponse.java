package br.dev.diisk.application.dtos.income;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class IncomeResponse {
    private Long id;
    private String description;
    private String categoryName;
    private BigDecimal value;
    private String date;

}
