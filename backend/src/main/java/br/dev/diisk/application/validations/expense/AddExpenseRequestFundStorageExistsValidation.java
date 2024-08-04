package br.dev.diisk.application.validations.expense;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.expense.IAddExpenseRequestValidator;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddExpenseRequestFundStorageExistsValidation implements IAddExpenseRequestValidator {

    private IListFundStorageCase listFundStorageCase;

    public AddExpenseRequestFundStorageExistsValidation(IListFundStorageCase listFundStorageCase) {
        this.listFundStorageCase = listFundStorageCase;
    }

    @Override
    public void validate(List<AddExpenseRequest> dtos, User user) {
        Set<FundStorage> resources = listFundStorageCase.execute(user.getId());
        for(AddExpenseRequest dto : dtos){
            if (resources.stream().noneMatch(resource -> resource.getId()==dto.getResourceId()))
            throw new DbValueNotFoundException("id", FundStorage.class.getSimpleName());
        }
    }

}
