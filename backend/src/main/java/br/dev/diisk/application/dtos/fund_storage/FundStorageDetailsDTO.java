package br.dev.diisk.application.dtos.fund_storage;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FundStorageDetailsDTO {
    private Long id;
    private String name;
    private Boolean creditCard;
    private BigDecimal balance;
}
