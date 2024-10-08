package br.dev.diisk.application.dtos.fund_storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateFundStorageRequest {
    private String name;
    private Boolean creditCard;
    private Boolean active;
}
