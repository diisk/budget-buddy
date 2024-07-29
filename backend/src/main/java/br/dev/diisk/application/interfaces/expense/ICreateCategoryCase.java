package br.dev.diisk.application.interfaces.expense;

import br.dev.diisk.application.dtos.expense.CreateCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateCategoryCase {

    ExpenseCategory execute(CreateCategoryRequest dto, User owner);

}
