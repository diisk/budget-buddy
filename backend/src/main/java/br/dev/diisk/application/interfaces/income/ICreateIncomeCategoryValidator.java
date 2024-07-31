package br.dev.diisk.application.interfaces.income;


import br.dev.diisk.application.dtos.income.CreateIncomeCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface ICreateIncomeCategoryValidator {

    void validate(CreateIncomeCategoryRequest dto, User user);

}
