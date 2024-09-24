package br.dev.diisk.application.validations.transaction_category;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.UtilService;
import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.exceptions.persistence.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryRequestValidator;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddTransactionCategoryRequestTransactionCategoryExistsValidation
        implements IAddTransactionCategoryRequestValidator {

    private final IListTransactionCategoryCase listTransactionCategoryCase;
    private final UtilService utilService;

    @Override
    public void validate(AddTransactionCategoryRequest dto, User user) {
        Set<TransactionCategory> categories = listTransactionCategoryCase.execute(user.getId(), dto.getType());
        if (categories.stream()
                .anyMatch(category -> utilService.equalsIgnoreCaseAndAccents(category.getName(), dto.getName())))
            throw new ValueAlreadyInDatabaseException(getClass(), "name");
    }

}
