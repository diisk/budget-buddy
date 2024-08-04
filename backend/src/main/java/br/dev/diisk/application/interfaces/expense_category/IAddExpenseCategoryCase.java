package br.dev.diisk.application.interfaces.expense_category;

import br.dev.diisk.application.dtos.expense_category.AddExpenseCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;

public interface IAddExpenseCategoryCase {

    ExpenseCategory execute(AddExpenseCategoryRequest dto, User owner);

}
