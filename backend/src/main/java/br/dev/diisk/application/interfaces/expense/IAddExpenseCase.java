package br.dev.diisk.application.interfaces.expense;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.user.User;

public interface IAddExpenseCase {

    Expense execute(AddExpenseRequest dto, User owner);

}
