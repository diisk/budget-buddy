package br.dev.diisk.application.validations.transaction;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionRequestValidator;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

@Component
public class UpdateTransactionRequestTransactionCategoryAndTypeValidation implements IUpdateTransactionRequestValidator {

    private final IListTransactionCategoryCase listTransactionCategoryCase;

    public UpdateTransactionRequestTransactionCategoryAndTypeValidation(
            IListTransactionCategoryCase listTransactionCategoryCase) {
        this.listTransactionCategoryCase = listTransactionCategoryCase;
    }

    @Override
    public void validate(Long id, UpdateTransactionRequest dto, User user) {
        Set<TransactionCategory> categories = new HashSet<>();
        for (TransactionTypeEnum type : TransactionTypeEnum.values()) {
            Set<TransactionCategory> typeCategories = listTransactionCategoryCase.execute(user.getId(),
                    type.toString());
            categories.addAll(typeCategories);
        }
        if (categories.stream().noneMatch(category -> category.getId() == dto.getCategoryId()))
                throw new DbValueNotFoundException(TransactionCategory.class, "id");
    }

}
