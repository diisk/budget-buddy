package br.dev.diisk.application.interfaces.expense;

import br.dev.diisk.application.dtos.expense.UpdateExpenseRequest;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateExpenseCase {

    Expense execute(Long id, UpdateExpenseRequest dto, User owner);

}
