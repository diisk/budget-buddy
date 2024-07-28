package br.dev.diisk.domain.entities;

import br.dev.diisk.application.dtos.income.RuleDescriptionDTO;
import br.dev.diisk.domain.enums.TypeRuleDescription;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RuleDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(value = AccessLevel.NONE)
    private Boolean negative;

    @Enumerated(EnumType.STRING)
    private TypeRuleDescription type;
    private String value;

    public Boolean isNegative() {
        return negative;
    }

    public RuleDescription(RuleDescriptionDTO dto) {
        this.negative = dto.isNegative();
        this.type = dto.getType();
        this.value = dto.getValue();
    }
}
