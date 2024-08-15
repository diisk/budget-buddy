package br.dev.diisk.application.dtos.transaction_category;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryTransactionDetailsDTO {

    @JsonIgnore
    private Long id;
    
    private String categoryName;
    private BigDecimal value;
    private BigDecimal budgetLimit;
    private BigDecimal percentageUsed;
}
