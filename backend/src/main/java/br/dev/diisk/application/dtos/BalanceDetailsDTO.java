package br.dev.diisk.application.dtos;

import java.math.BigDecimal;
import java.util.List;

import br.dev.diisk.application.dtos.fund_storage.FundStorageDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BalanceDetailsDTO {
    private BigDecimal totalBalance;
    private List<FundStorageDetailsDTO> fundStorageBalances;
}
