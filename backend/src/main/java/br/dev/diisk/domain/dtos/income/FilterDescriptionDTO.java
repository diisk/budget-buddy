package br.dev.diisk.domain.dtos.income;

import java.util.List;

import br.dev.diisk.domain.entities.FilterDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FilterDescriptionDTO {
    private List<RuleDescriptionDTO> rules;

    public FilterDescriptionDTO(FilterDescription entity){
        this.rules = entity.getRules().stream().map(RuleDescriptionDTO::new).toList();
    }
}
