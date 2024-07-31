package br.dev.diisk.application.cases.expense;

import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.expense.ICreateExpenseCategoryValidator;
import br.dev.diisk.application.interfaces.expense.ICreateExpenseCategoryCase;
import br.dev.diisk.application.dtos.expense.CreateExpenseCategoryRequest;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddExpenseCategoryCase implements ICreateExpenseCategoryCase {

    private List<ICreateExpenseCategoryValidator> validators;
    private IExpenseCategoryRepository expenseCategoryRepository;

    public AddExpenseCategoryCase(List<ICreateExpenseCategoryValidator> validators,
            IExpenseCategoryRepository expenseCategoryRepository) {
        this.validators = validators;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    @Transactional
    public ExpenseCategory execute(CreateExpenseCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(dto.getName());
        expenseCategory.setUser(owner);
        return expenseCategoryRepository.save(expenseCategory);
    }

}
