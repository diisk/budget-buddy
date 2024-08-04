package br.dev.diisk.application.mappers.expense;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.expense_category.IListExpensesCategoriesCase;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;

@Component
public class AddExpenseRequestMapper extends BaseMapper<AddExpenseRequest, Expense> {

    private IListFundStorageCase listFundStorageCase;
    private IListExpensesCategoriesCase listExpensesCategoriesCase;

    public AddExpenseRequestMapper(ModelMapper mapper, IListFundStorageCase listFundStorageCase,
            IListExpensesCategoriesCase listExpensesCategoriesCase) {
        super(mapper);
        this.listFundStorageCase = listFundStorageCase;
        this.listExpensesCategoriesCase = listExpensesCategoriesCase;
    }

    @Override
    protected void doComplexMap(AddExpenseRequest source, Expense target) {
        Set<FundStorage> storages = listFundStorageCase.execute(source.getUser().getId());
        Set<ExpenseCategory> categories = listExpensesCategoriesCase.execute(source.getUser().getId());

        FundStorage storage = storages.stream()
                .filter(res -> res.getId() == source.getFundStorageId()).findFirst().get();
        ExpenseCategory category = categories.stream()
                .filter(cat -> cat.getId() == source.getCategoryId()).findFirst().get();

        target.setUser(source.getUser());
        target.setCategory(category);
        target.setStorage(storage);
    }

}
