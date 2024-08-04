package br.dev.diisk.application.dtos.expense_category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExpenseCategoryResponse {

    private Long id;
    private String name;
    
}
