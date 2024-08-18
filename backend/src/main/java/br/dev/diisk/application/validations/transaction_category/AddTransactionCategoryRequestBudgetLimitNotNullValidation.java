package br.dev.diisk.application.validations.transaction_category;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.exceptions.FieldNotNullException;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryRequestValidator;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

@Component
public class AddTransactionCategoryRequestBudgetLimitNotNullValidation
        implements IAddTransactionCategoryRequestValidator {

    @Override
    public void validate(AddTransactionCategoryRequest dto, User user) {
        if (dto.getType().equalsIgnoreCase(TransactionTypeEnum.EXPENSE.toString())
                && dto.getBudgetLimit() == null)
            throw new FieldNotNullException(AddTransactionCategoryRequest.class, "budgetLimit");
    }

}
