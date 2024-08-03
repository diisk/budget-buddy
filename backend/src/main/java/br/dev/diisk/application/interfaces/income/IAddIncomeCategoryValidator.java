package br.dev.diisk.application.interfaces.income;


import br.dev.diisk.application.dtos.income.AddIncomeCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCategoryValidator {

    void validate(AddIncomeCategoryRequest dto, User user);

}
