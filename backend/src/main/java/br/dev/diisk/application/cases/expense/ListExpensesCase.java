package br.dev.diisk.application.cases.expense;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.expense.IListExpensesCase;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.repositories.expense.IExpenseRepository;

@Service
public class ListExpensesCase implements IListExpensesCase{

    private IExpenseRepository expenseRepository;

    public ListExpensesCase(IExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Cacheable(value = "expenses", key = "#userId+'-'+#beginsAt+'-'+#endsAt")
    public Set<Expense> execute(Long userId, LocalDateTime beginsAt, LocalDateTime endsAt) {
        if(endsAt==null) endsAt = LocalDateTime.now();
        return expenseRepository.findAllByUserIdAndPeriod(userId, beginsAt, endsAt);
    }

}
