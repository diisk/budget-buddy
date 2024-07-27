package br.dev.diisk.domain.cases.income;

import br.dev.diisk.domain.dtos.income.UpdateIncomeDTO;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateIncomeCase {

    Income execute(Long id, UpdateIncomeDTO dto, User owner);

}
