package br.dev.diisk.application.dtos.expense;

import java.util.List;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateExpenseCategoryRequest {
    @NotBlank(message = GlobalMessages.BLANK_OR_NULL_FIELD)
    private String name;
    private List<FilterDescriptionDTO> filters;
}
