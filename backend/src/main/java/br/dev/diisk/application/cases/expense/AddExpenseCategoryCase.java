package br.dev.diisk.application.cases.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.interfaces.expense.CreateExpenseCategoryValidator;
import br.dev.diisk.application.interfaces.expense.ICreateCategoryCase;
import br.dev.diisk.application.dtos.expense.CreateCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import jakarta.transaction.Transactional;

@Component
public class AddExpenseCategoryCase implements ICreateCategoryCase {

    @Autowired
    private List<CreateExpenseCategoryValidator> validators;

    @Autowired
    private IExpenseCategoryRepository expenseCategoryRepository;

    @Override
    @Transactional
    public ExpenseCategory execute(CreateCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setUser(owner);
        return expenseCategoryRepository.save(expenseCategory);
    }

}
