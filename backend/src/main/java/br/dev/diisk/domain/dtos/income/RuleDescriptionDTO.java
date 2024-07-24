package br.dev.diisk.domain.dtos.income;

import br.dev.diisk.domain.enums.TypeRuleDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class RuleDescriptionDTO {
    private Boolean negative;
    private TypeRuleDescription type;
    private String value;
}
