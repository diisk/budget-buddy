package br.dev.diisk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeRuleDescriptionEnum {
    EQUAL("Igual"),
    START("Começa"),
    END("Termina"),
    ;

    private String title;
}
