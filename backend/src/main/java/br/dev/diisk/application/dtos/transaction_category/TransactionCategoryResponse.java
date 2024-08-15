package br.dev.diisk.application.dtos.transaction_category;

import java.math.BigDecimal;

import br.dev.diisk.domain.enums.TransactionTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TransactionCategoryResponse {

    private Long id;
    private String name;
    private BigDecimal budgetLimit;
    private TransactionTypeEnum type;
    
}
