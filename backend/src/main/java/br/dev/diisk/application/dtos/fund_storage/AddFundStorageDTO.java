package br.dev.diisk.application.dtos.fund_storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddFundStorageDTO {
    private String name;
    private Boolean creditCard;
}
