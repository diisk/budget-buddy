package br.dev.diisk.application.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HistoryDTO {
    private String month;
    private BigDecimal amount;
}
