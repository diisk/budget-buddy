package br.dev.diisk.application.mappers.transaction;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;

@Component
public class AddTransactionRequestToTransactionMapper extends BaseMapper<AddTransactionRequest, Transaction> {

    private final IListFundStorageCase listFundStorageCase;
    private final IListTransactionCategoryCase listTransactionCategoryCase;

    public AddTransactionRequestToTransactionMapper(ModelMapper mapper, IListFundStorageCase listFundStorageCase,
            IListTransactionCategoryCase listTransactionCategoryCase) {
        super(mapper);
        this.listFundStorageCase = listFundStorageCase;
        this.listTransactionCategoryCase = listTransactionCategoryCase;
    }

    @Override
    protected void doComplexMap(AddTransactionRequest source, Transaction target) {
        Set<FundStorage> storages = listFundStorageCase.execute(source.getUser().getId());
        Set<TransactionCategory> categories = listTransactionCategoryCase.execute(source.getUser().getId(),
                source.getType());

        FundStorage storage = storages.stream()
                .filter(res -> res.getId() == source.getFundStorageId()).findFirst().get();
        TransactionCategory category = categories.stream()
                .filter(cat -> cat.getId() == source.getCategoryId()).findFirst().get();

        target.setUser(source.getUser());
        target.setCategory(category);
        target.setStorage(storage);
    }

}
