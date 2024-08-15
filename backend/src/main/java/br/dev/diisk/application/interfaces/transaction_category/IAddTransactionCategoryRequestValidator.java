package br.dev.diisk.application.interfaces.transaction_category;


import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddTransactionCategoryRequestValidator {

    void validate(AddTransactionCategoryRequest dto, User user);

}
