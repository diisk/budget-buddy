package br.dev.diisk.application.mappers.transaction;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.transaction.TransactionResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.transaction.Transaction;

@Component
public class TransactionToTransactionResponseMapper extends BaseMapper<Transaction, TransactionResponse> {

    public TransactionToTransactionResponseMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void doComplexMap(Transaction source, TransactionResponse target) {
        target.setCategoryName(source.getCategory().getName());
        target.setStorageName(source.getStorage().getName());
        target.setType(source.getCategory().getType());
    }

}
