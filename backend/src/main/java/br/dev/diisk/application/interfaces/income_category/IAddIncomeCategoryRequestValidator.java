package br.dev.diisk.application.interfaces.income_category;


import br.dev.diisk.application.dtos.income_category.AddIncomeCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddIncomeCategoryRequestValidator {

    void validate(AddIncomeCategoryRequest dto, User user);

}
