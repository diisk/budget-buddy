package br.dev.diisk.application.cases.expense;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.UpdateExpenseRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.expense.IUpdateExpenseCase;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;
import br.dev.diisk.domain.repositories.expense.IExpenseRepository;
import jakarta.transaction.Transactional;

@Component
public class UpdateExpenseCase implements IUpdateExpenseCase {

    @Autowired
    private IExpenseRepository expenseRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IExpenseCategoryRepository expenseCategoryRepository;

    @Override
    @Transactional
    public Expense execute(Long id, UpdateExpenseRequest dto, User owner) {
        Optional<Expense> foundedExpense = expenseRepository.findById(id);
        if (foundedExpense.isEmpty())
            throw new DbValueNotFoundException("id", Expense.class.getSimpleName());

        Expense expense = foundedExpense.get();

        if (dto.getCategoryId() != null) {
            Optional<ExpenseCategory> foundedCategory = expenseCategoryRepository
                    .findByIdAndUserId(dto.getCategoryId(), owner.getId());

            if (foundedCategory.isEmpty())
                throw new DbValueNotFoundException("id", ExpenseCategory.class.getSimpleName());

            expense.setCategory(foundedCategory.get());
        }

        mapper.map(dto, expense);

        return expenseRepository.save(expense);

    }

}
