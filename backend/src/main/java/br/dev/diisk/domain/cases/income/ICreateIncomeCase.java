package br.dev.diisk.domain.cases.income;

import br.dev.diisk.application.dtos.income.CreateIncomeRequest;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateIncomeCase {

    Income execute(CreateIncomeRequest dto, User owner);

}
