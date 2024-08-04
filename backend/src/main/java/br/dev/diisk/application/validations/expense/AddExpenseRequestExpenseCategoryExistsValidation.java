package br.dev.diisk.application.validations.expense;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.expense.IAddExpenseRequestValidator;
import br.dev.diisk.application.interfaces.expense_category.IListExpensesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddExpenseRequestExpenseCategoryExistsValidation implements IAddExpenseRequestValidator {

    private IListExpensesCategoriesCase listExpensesCategoriesCase;

    public AddExpenseRequestExpenseCategoryExistsValidation(IListExpensesCategoriesCase listExpensesCategoriesCase) {
        this.listExpensesCategoriesCase = listExpensesCategoriesCase;
    }

    @Override
    public void validate(List<AddExpenseRequest> dtos, User user) {
        Set<ExpenseCategory> categories = listExpensesCategoriesCase.execute(user.getId());
        for(AddExpenseRequest dto : dtos){
            if (categories.stream().noneMatch(category -> category.getId()==dto.getCategoryId()))
            throw new DbValueNotFoundException("id", ExpenseCategory.class.getSimpleName());
        }
    }

}
