package br.dev.diisk.application.dtos.transaction;

import java.math.BigDecimal;

import br.dev.diisk.domain.enums.TransactionTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TransactionResponse {
    private Long id;
    private String description;
    private String categoryName;
    private String storageName;
    private TransactionTypeEnum type;
    private BigDecimal value;
    private String date;

}
