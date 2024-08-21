package br.dev.diisk.application.dtos.fund_storage;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateFundStorageDTO {
    private String name;
    private Boolean creditCard;
    private Boolean active;
    private BigDecimal balance;
}
