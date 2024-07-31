package br.dev.diisk.application.interfaces.expense;


import br.dev.diisk.application.dtos.expense.CreateExpenseCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateExpenseCategoryValidator {

    void validate(CreateExpenseCategoryRequest dto, User user);

}
