package br.dev.diisk.application.interfaces.expense;


import br.dev.diisk.application.dtos.expense.CreateCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface CreateExpenseCategoryValidator {

    void validate(CreateCategoryRequest dto, User user);

}
