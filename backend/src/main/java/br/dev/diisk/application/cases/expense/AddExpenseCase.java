package br.dev.diisk.application.cases.expense;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.CreateExpenseRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.expense.ICreateExpenseCase;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import br.dev.diisk.domain.repositories.expense.IExpenseRepository;
import jakarta.transaction.Transactional;

@Component
public class AddExpenseCase implements ICreateExpenseCase {

    @Autowired
    private IExpenseRepository expenseRepository;

    @Autowired
    private IExpenseCategoryRepository expenseCategoryRepository;

    @Override
    @Transactional
    public Expense execute(CreateExpenseRequest dto, User owner) {
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
