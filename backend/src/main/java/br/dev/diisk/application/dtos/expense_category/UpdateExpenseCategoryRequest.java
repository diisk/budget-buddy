package br.dev.diisk.application.dtos.expense_category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateExpenseCategoryRequest {
    private String name;
    private Boolean active;
}
