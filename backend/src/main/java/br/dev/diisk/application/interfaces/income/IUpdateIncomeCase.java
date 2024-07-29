package br.dev.diisk.application.interfaces.income;

import br.dev.diisk.application.dtos.income.UpdateIncomeRequest;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateIncomeCase {

    Income execute(Long id, UpdateIncomeRequest dto, User owner);

}
