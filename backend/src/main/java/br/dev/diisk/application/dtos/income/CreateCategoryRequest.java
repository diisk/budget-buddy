package br.dev.diisk.application.dtos.income;

import java.util.List;

import br.dev.diisk.domain.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateCategoryRequest {
    @NotBlank(message = GlobalMessages.BLANK_FIELD)
    private String name;
    private List<FilterDescriptionDTO> filters;
}
