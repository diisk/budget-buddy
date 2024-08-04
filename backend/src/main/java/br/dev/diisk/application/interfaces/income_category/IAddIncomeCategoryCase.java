package br.dev.diisk.application.interfaces.income_category;

import br.dev.diisk.application.dtos.income_category.AddIncomeCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCategoryCase {

    IncomeCategory execute(AddIncomeCategoryRequest dto, User owner);

}
