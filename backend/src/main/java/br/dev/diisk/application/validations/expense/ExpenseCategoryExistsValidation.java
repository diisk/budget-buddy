package br.dev.diisk.application.validations.expense;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCategoryValidator;
import br.dev.diisk.application.dtos.expense.AddExpenseCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;

@Component
public class ExpenseCategoryExistsValidation implements IAddExpenseCategoryValidator {

    private IExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryExistsValidation(IExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public void validate(AddExpenseCategoryRequest dto, User user) {
        if (expenseCategoryRepository.existsByNameAndUserId(dto.getName(), user.getId()))
            throw new ValueAlreadyInDatabaseException("name", ExpenseCategory.class.getSimpleName());
    }

}
