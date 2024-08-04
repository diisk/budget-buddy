package br.dev.diisk.application.dtos.fund_storage;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddFundStorageRequest {
    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String name;
    @NotNull(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private Boolean creditCard;
}
