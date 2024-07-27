package br.dev.diisk.domain.cases.income;

import br.dev.diisk.domain.dtos.income.CreateIncomeDTO;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCase {

    Income execute(CreateIncomeDTO dto, User owner);

}
