package br.dev.diisk.application.interfaces.expense;

import br.dev.diisk.application.dtos.expense.CreateExpenseCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateExpenseCategoryCase {

    ExpenseCategory execute(CreateExpenseCategoryRequest dto, User owner);

}
