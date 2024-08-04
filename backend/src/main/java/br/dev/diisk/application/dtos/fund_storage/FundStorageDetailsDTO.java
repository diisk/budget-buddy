package br.dev.diisk.application.dtos.fund_storage;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FundStorageDetailsDTO {
    @JsonIgnore
    private Long id;
    
    private String name;
    private Boolean creditCard;
    private BigDecimal balance;
}
