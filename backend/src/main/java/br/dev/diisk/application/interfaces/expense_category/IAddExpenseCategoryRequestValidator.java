package br.dev.diisk.application.interfaces.expense_category;


import br.dev.diisk.application.dtos.expense_category.AddExpenseCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddExpenseCategoryRequestValidator {

    void validate(AddExpenseCategoryRequest dto, User user);

}
