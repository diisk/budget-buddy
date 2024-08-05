package br.dev.diisk.application.cases.expense_category;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.expense_category.AddExpenseCategoryRequest;
import br.dev.diisk.application.interfaces.expense_category.IAddExpenseCategoryCase;
import br.dev.diisk.application.interfaces.expense_category.IAddExpenseCategoryRequestValidator;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddExpenseCategoryCase implements IAddExpenseCategoryCase {

    private List<IAddExpenseCategoryRequestValidator> validators;
    private IExpenseCategoryRepository expenseCategoryRepository;

    public AddExpenseCategoryCase(List<IAddExpenseCategoryRequestValidator> validators,
            IExpenseCategoryRepository expenseCategoryRepository) {
        this.validators = validators;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "expenses-categories", allEntries = true)
    public ExpenseCategory execute(AddExpenseCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(dto.getName());
        expenseCategory.setBudgetLimit(dto.getBudgetLimit());
        expenseCategory.setUser(owner);
        return expenseCategoryRepository.save(expenseCategory);
    }

}
