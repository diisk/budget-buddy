package br.dev.diisk.application.interfaces.income;

import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCase {

    Income execute(AddIncomeRequest dto, User owner);

}
