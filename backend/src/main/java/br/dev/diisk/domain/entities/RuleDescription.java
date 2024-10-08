package br.dev.diisk.domain.entities;

import br.dev.diisk.domain.enums.TypeRuleDescriptionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "rules_descriptions")
public class RuleDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(value = AccessLevel.NONE)
    private Boolean negative;

    @Enumerated(EnumType.STRING)
    private TypeRuleDescriptionEnum type;
    private String value;

    public Boolean isNegative() {
        return negative;
    }

}
