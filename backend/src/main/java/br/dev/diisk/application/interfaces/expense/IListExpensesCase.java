package br.dev.diisk.application.interfaces.expense;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.expense.Expense;

public interface IListExpensesCase {
    
    Set<Expense> execute(Long userId, LocalDateTime beginsAt, LocalDateTime endsAt);
    
}
