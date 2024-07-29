package br.dev.diisk.application.dtos.income;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateCategoryRequest {
    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String name;
}
