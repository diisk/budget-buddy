package br.dev.diisk.application.interfaces.expense;

import br.dev.diisk.application.dtos.expense.CreateExpenseRequest;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateExpenseCase {

    Expense execute(CreateExpenseRequest dto, User owner);

}
