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

    private IListFundStorageCase listBalanceResourceCase;

    public AddIncomeRequestFundStorageExistsValidation(IListFundStorageCase listBalanceResourceCase) {
        this.listBalanceResourceCase = listBalanceResourceCase;
    }

    @Override
    public void validate(List<AddIncomeRequest> dtos, User user) {
        Set<FundStorage> resources = listBalanceResourceCase.execute(user.getId());
        for (AddIncomeRequest dto : dtos) {
            if (resources.stream().noneMatch(resource -> resource.getId() == dto.getFundStorageId()))
                throw new DbValueNotFoundException("id", FundStorage.class.getSimpleName());
        }
    }
}
