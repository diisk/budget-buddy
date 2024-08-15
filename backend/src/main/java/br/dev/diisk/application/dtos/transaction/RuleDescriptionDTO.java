package br.dev.diisk.application.dtos.transaction;

import br.dev.diisk.domain.entities.RuleDescription;
import br.dev.diisk.domain.enums.TypeRuleDescriptionEnum;
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
    private TypeRuleDescriptionEnum type;
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
