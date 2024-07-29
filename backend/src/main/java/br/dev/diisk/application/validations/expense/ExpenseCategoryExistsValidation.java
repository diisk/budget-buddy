package br.dev.diisk.application.validations.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.expense.CreateExpenseCategoryValidator;
import br.dev.diisk.application.dtos.expense.CreateCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;

@Component
public class ExpenseCategoryExistsValidation implements CreateExpenseCategoryValidator {

    @Autowired
    private IExpenseCategoryRepository expenseCategoryRepository;

    @Override
    public void validate(CreateCategoryRequest dto, User user) {
        if (expenseCategoryRepository.existsByNameAndUserId(dto.getName(), user.getId()))
            throw new ValueAlreadyInDatabaseException("name", ExpenseCategory.class.getSimpleName());
    }

}
