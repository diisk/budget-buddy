package br.dev.diisk.application.interfaces.income;


import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface CreateIncomeCategoryValidator {

    void validate(CreateCategoryRequest dto, User user);

}
