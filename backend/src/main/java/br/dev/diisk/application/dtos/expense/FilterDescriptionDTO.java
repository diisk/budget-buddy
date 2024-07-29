package br.dev.diisk.application.dtos.expense;

import java.util.List;

import br.dev.diisk.domain.entities.FilterDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FilterDescriptionDTO {
    private List<RuleDescriptionDTO> rules;

    public FilterDescriptionDTO(FilterDescription entity){
        this.rules = entity.getRules().stream().map(RuleDescriptionDTO::new).toList();
    }
}
