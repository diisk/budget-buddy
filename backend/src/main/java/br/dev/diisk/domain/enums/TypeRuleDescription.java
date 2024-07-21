package br.dev.diisk.domain.enums;

public enum TypeRuleDescription {
    EQUAL("Igual"),
    START("Começa"),
    END("Termina"),
    ;

    private String title;

    TypeRuleDescription(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
