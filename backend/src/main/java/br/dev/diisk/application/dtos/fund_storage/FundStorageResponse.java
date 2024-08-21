package br.dev.diisk.application.dtos.fund_storage;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FundStorageResponse {
    private Long id;
    private String name;
    private Boolean creditCard;
    private BigDecimal balance;
}
