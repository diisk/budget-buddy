package br.dev.diisk.application.validations.transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.exceptions.EnumValueNotFoundException;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

@Component
public class AddTransactionRequestTransactionCategoryAndTypeValidation implements IAddTransactionRequestValidator {

    private final IListTransactionCategoryCase listTransactionCategoryCase;

    public AddTransactionRequestTransactionCategoryAndTypeValidation(
            IListTransactionCategoryCase listTransactionCategoryCase) {
        this.listTransactionCategoryCase = listTransactionCategoryCase;
    }

    @Override
    public void validate(List<AddTransactionRequest> dtos, User user) {
        Set<TransactionCategory> categories = new HashSet<>();
        for (TransactionTypeEnum type : TransactionTypeEnum.values()) {
            Set<TransactionCategory> typeCategories = listTransactionCategoryCase.execute(user.getId(),
                    type.toString());
            categories.addAll(typeCategories);
        }
        for (AddTransactionRequest dto : dtos) {
            Optional<TransactionTypeEnum> type = TransactionTypeEnum.getByName(dto.getType());
            if (type.isEmpty())
                throw new EnumValueNotFoundException(TransactionTypeEnum.class, dto.getType());

            if (categories.stream().noneMatch(category -> category.getId() == dto.getCategoryId()
                    && type.get() == category.getType()))
                throw new DbValueNotFoundException(TransactionCategory.class, "id");
        }
    }

}
