package br.dev.diisk.application.interfaces.expense;


import br.dev.diisk.application.dtos.expense.AddExpenseCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddExpenseCategoryValidator {

    void validate(AddExpenseCategoryRequest dto, User user);

}
