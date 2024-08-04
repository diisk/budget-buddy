package br.dev.diisk.application.validations.income;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.income.IAddIncomeRequestValidator;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddIncomeRequestFundStorageExistsValidation implements IAddIncomeRequestValidator {

    private IListFundStorageCase listFundStorageCase;

    public AddIncomeRequestFundStorageExistsValidation(IListFundStorageCase listFundStorageCase) {
        this.listFundStorageCase = listFundStorageCase;
    }

    @Override
    public void validate(List<AddIncomeRequest> dtos, User user) {
        Set<FundStorage> storages = listFundStorageCase.execute(user.getId());
        for (AddIncomeRequest dto : dtos) {
            if (storages.stream().noneMatch(storage -> storage.getId() == dto.getFundStorageId()))
                throw new DbValueNotFoundException("id", FundStorage.class.getSimpleName());
        }
    }
}
