package br.dev.diisk.domain.enums;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionTypeEnum {
    INCOME("Income"),
    EXPENSE("Expense");

    private String description;

    public static Optional<TransactionTypeEnum> getByName(String name) {
        for (TransactionTypeEnum type : values()) {
            if (type.toString().equalsIgnoreCase(name))
                return Optional.of(type);
        }
        return Optional.empty();
    }

}
