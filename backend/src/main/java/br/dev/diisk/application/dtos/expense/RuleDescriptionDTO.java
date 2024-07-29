package br.dev.diisk.application.dtos.expense;

import br.dev.diisk.domain.entities.RuleDescription;
import br.dev.diisk.domain.enums.TypeRuleDescription;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RuleDescriptionDTO {
    @Getter(value = AccessLevel.NONE)
    private Boolean negative;
    private TypeRuleDescription type;
    private String value;

    public Boolean isNegative() {
        return negative;
    }

    public RuleDescriptionDTO(RuleDescription entity) {
        this.negative = entity.isNegative();
        this.type = entity.getType();
        this.value = entity.getValue();
    }
}
