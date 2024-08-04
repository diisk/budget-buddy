package br.dev.diisk.application.dtos.income_category;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryIncomesDetailsDTO {

    @JsonIgnore
    private Long id;

    private String categoryName;
    private BigDecimal value;
}
