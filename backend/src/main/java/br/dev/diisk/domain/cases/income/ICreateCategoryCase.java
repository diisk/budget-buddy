package br.dev.diisk.domain.cases.income;

import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateCategoryCase {

    IncomeCategory execute(CreateCategoryRequest dto, User owner);

}
