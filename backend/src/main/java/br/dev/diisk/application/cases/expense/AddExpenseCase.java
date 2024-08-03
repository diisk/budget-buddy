package br.dev.diisk.application.cases.expense;

import java.util.Optional;

import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCase;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import br.dev.diisk.domain.repositories.expense.IExpenseRepository;
import jakarta.transaction.Transactional;

@Service
public class AddExpenseCase implements IAddExpenseCase {

    private IExpenseRepository expenseRepository;
    private IExpenseCategoryRepository expenseCategoryRepository;
    
    public AddExpenseCase(IExpenseRepository expenseRepository, IExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    @Transactional
    public Expense execute(AddExpenseRequest dto, User owner) {
        Optional<ExpenseCategory> foundedCategory = expenseCategoryRepository.findByIdAndUserId(dto.getCategoryId(),
                owner.getId());

        if (foundedCategory.isEmpty())
            throw new DbValueNotFoundException("id", ExpenseCategory.class.getSimpleName());

        Expense expense = new Expense();
        expense.setCategory(foundedCategory.get());
        expense.setUser(owner);
        expense.setDate(dto.getDate());
        expense.setValue(dto.getValue());
        expense.setDescription(dto.getDescription());
        return expenseRepository.save(expense);
    }

}
