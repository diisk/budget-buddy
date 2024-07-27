package br.dev.diisk.domain.cases.income;

import br.dev.diisk.domain.dtos.income.CreateIncomeCategoryDTO;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCategoryCase {

    IncomeCategory execute(CreateIncomeCategoryDTO dto, User owner);

}
