package br.dev.diisk.application.interfaces.expense;


import java.util.List;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddExpenseRequestValidator {

    void validate(List<AddExpenseRequest> dtos, User user);

}
