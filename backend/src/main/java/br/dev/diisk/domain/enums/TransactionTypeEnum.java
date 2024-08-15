package br.dev.diisk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionTypeEnum {
    INCOME("Income"),
    EXPENSE("Expense");

    private String description;

}
