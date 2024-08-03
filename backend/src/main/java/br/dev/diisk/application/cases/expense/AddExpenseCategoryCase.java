package br.dev.diisk.application.cases.expense;

import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCategoryValidator;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCategoryCase;
import br.dev.diisk.application.dtos.expense.AddExpenseCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddExpenseCategoryCase implements IAddExpenseCategoryCase {

    private List<IAddExpenseCategoryValidator> validators;
    private IExpenseCategoryRepository expenseCategoryRepository;

    public AddExpenseCategoryCase(List<IAddExpenseCategoryValidator> validators,
            IExpenseCategoryRepository expenseCategoryRepository) {
        this.validators = validators;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    @Transactional
    public ExpenseCategory execute(AddExpenseCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(dto.getName());
        expenseCategory.setUser(owner);
        return expenseCategoryRepository.save(expenseCategory);
    }

}
