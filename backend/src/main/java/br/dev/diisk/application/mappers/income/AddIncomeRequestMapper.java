package br.dev.diisk.application.mappers.income;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.income_category.IListIncomesCategoriesCase;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;

@Component
public class AddIncomeRequestMapper extends BaseMapper<AddIncomeRequest, Income> {

    private IListFundStorageCase listFundStorageCase;
    private IListIncomesCategoriesCase listIncomesCategoriesCase;

    public AddIncomeRequestMapper(ModelMapper mapper, IListFundStorageCase listFundStorageCase,
            IListIncomesCategoriesCase listIncomesCategoriesCase) {
        super(mapper);
        this.listFundStorageCase = listFundStorageCase;
        this.listIncomesCategoriesCase = listIncomesCategoriesCase;
    }

    @Override
    protected void doComplexMap(AddIncomeRequest source, Income target) {
        Set<FundStorage> storages = listFundStorageCase.execute(source.getUser().getId());
        Set<IncomeCategory> categories = listIncomesCategoriesCase.execute(source.getUser().getId());

        FundStorage storage = storages.stream()
                .filter(res -> res.getId() == source.getFundStorageId()).findFirst().get();
        IncomeCategory category = categories.stream()
                .filter(cat -> cat.getId() == source.getCategoryId()).findFirst().get();

        target.setUser(source.getUser());
        target.setCategory(category);
        target.setStorage(storage);
    }

}
