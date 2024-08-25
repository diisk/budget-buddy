package br.dev.diisk.application.dtos.fund_storage;

import java.math.BigDecimal;

import br.dev.diisk.domain.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionValueDTO {
    private BigDecimal value;
    private TransactionTypeEnum type;
    private Boolean undo;
}
