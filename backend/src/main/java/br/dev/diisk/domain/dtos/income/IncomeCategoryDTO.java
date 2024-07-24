package br.dev.diisk.domain.dtos.income;

import java.util.List;

import br.dev.diisk.application.GlobalMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IncomeCategoryDTO {
    @NotBlank(message = GlobalMessages.BLANK_FIELD)
    private String name;
    private List<FilterDescriptionDTO> filters;
}
