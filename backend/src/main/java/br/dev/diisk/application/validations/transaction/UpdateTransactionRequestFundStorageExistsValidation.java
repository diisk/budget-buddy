package br.dev.diisk.application.validations.transaction;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionRequestValidator;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

@Component
public class UpdateTransactionRequestFundStorageExistsValidation implements IUpdateTransactionRequestValidator {

    private final IListFundStorageCase listFundStorageCase;

    public UpdateTransactionRequestFundStorageExistsValidation(IListFundStorageCase listFundStorageCase) {
        this.listFundStorageCase = listFundStorageCase;
    }

    @Override
    public void validate(Long id, UpdateTransactionRequest dto, User user) {
        Set<FundStorage> storages = listFundStorageCase.execute(user.getId());
        if (storages.stream().noneMatch(storage -> storage.getId() == dto.getFundStorageId()))
                throw new DbValueNotFoundException(FundStorage.class, "id");
    }

}
