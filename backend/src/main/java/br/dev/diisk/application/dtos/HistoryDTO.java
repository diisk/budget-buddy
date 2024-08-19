package br.dev.diisk.application.dtos;

import java.math.BigDecimal;

import br.dev.diisk.domain.enums.TransactionTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HistoryDTO {
    private String monthName;
    private Integer month;
    private Integer year;
    private TransactionTypeEnum type;
    private BigDecimal value;
}
