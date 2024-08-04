package br.dev.diisk.application.validations.expense_category;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense_category.AddExpenseCategoryRequest;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.expense_category.IAddExpenseCategoryRequestValidator;
import br.dev.diisk.application.interfaces.expense_category.IListExpensesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddExpenseCategoryRequestExpenseCategoryExistsValidation implements IAddExpenseCategoryRequestValidator {

    private IListExpensesCategoriesCase listExpensesCategoriesCase;

    public AddExpenseCategoryRequestExpenseCategoryExistsValidation(IListExpensesCategoriesCase listExpensesCategoriesCase) {
        this.listExpensesCategoriesCase = listExpensesCategoriesCase;
    }

    @Override
    public void validate(AddExpenseCategoryRequest dto, User user) {
        Set<ExpenseCategory> categories = listExpensesCategoriesCase.execute(user.getId());
        if (categories.stream().anyMatch(category -> category.getName().equalsIgnoreCase(dto.getName())))
            throw new ValueAlreadyInDatabaseException("name", ExpenseCategory.class.getSimpleName());
    }

}
