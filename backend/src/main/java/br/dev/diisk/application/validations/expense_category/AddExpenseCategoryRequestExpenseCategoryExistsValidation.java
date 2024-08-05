package br.dev.diisk.application.validations.expense_category;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense_category.AddExpenseCategoryRequest;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.expense_category.IAddExpenseCategoryRequestValidator;
import br.dev.diisk.application.interfaces.expense_category.IListExpensesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.infra.services.UtilService;

@Component
public class AddExpenseCategoryRequestExpenseCategoryExistsValidation implements IAddExpenseCategoryRequestValidator {

    private IListExpensesCategoriesCase listExpensesCategoriesCase;
    private UtilService utilService;

    public AddExpenseCategoryRequestExpenseCategoryExistsValidation(
            IListExpensesCategoriesCase listExpensesCategoriesCase, UtilService utilService) {
        this.listExpensesCategoriesCase = listExpensesCategoriesCase;
        this.utilService = utilService;
    }

    @Override
    public void validate(AddExpenseCategoryRequest dto, User user) {
        Set<ExpenseCategory> categories = listExpensesCategoriesCase.execute(user.getId());
        if (categories.stream()
                .anyMatch(category -> utilService.equalsIgnoreCaseAndAccents(category.getName(), dto.getName())))
            throw new ValueAlreadyInDatabaseException("name", ExpenseCategory.class.getSimpleName());
    }

}
