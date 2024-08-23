package br.dev.diisk.application.mappers.transaction;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.transaction_category.IGetTransactionCategoryCase;
import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;

@Component
public class UpdateTransactionRequestToTransactionMapper extends BaseMapper<UpdateTransactionRequest, Transaction> {

        private final IListFundStorageCase listFundStorageCase;
        private final IGetTransactionCategoryCase getTransactionCategoryCase;

        public UpdateTransactionRequestToTransactionMapper(ModelMapper mapper, IListFundStorageCase listFundStorageCase,
                        IGetTransactionCategoryCase getTransactionCategoryCase) {
                super(mapper);
                this.listFundStorageCase = listFundStorageCase;
                this.getTransactionCategoryCase = getTransactionCategoryCase;
        }

        @Override
        protected void doComplexUpdate(UpdateTransactionRequest source, Transaction target) {
                if (source.getCategoryId() != null) {
                        TransactionCategory category = getTransactionCategoryCase.execute(source.getCategoryId(),
                                        source.getUser());
                        target.setCategory(category);
                }
                if (source.getFundStorageId() != null) {
                        Set<FundStorage> storages = listFundStorageCase.execute(source.getUser().getId());

                        FundStorage storage = storages.stream()
                                        .filter(res -> res.getId() == source.getFundStorageId()).findFirst().get();

                        target.setFundStorage(storage);
                }

        }

}
