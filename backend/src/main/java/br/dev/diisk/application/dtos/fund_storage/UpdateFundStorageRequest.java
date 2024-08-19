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
public class UpdateFundStorageRequest {
    private String name;
    private Boolean creditCard;
    private Boolean active;
    private BigDecimal balance;
}
