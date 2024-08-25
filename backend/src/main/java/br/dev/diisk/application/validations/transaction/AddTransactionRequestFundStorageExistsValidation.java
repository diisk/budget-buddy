package br.dev.diisk.application.validations.transaction;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddTransactionRequestFundStorageExistsValidation implements IAddTransactionRequestValidator {

    private final IListFundStorageCase listFundStorageCase;

    @Override
    public void validate(List<AddTransactionRequest> dtos, User user) {
        Set<FundStorage> storages = listFundStorageCase.execute(user.getId());
        for (AddTransactionRequest dto : dtos) {
            if (storages.stream().noneMatch(storage -> storage.getId() == dto.getFundStorageId()))
                throw new DbValueNotFoundException(getClass(), "id");
        }
    }

}
