package br.dev.diisk.application.interfaces.expense_category;

import java.util.Set;

import br.dev.diisk.domain.entities.expense.ExpenseCategory;

public interface IListExpensesCategoriesCase {
    
    Set<ExpenseCategory> execute(Long userId);
    
}
