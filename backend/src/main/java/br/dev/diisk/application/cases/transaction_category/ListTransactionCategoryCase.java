package br.dev.diisk.application.cases.transaction_category;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;

@Service
public class ListTransactionCategoryCase implements IListTransactionCategoryCase {

    private ITransactionCategoryRepository transactionCategoryRepository;

    public ListTransactionCategoryCase(ITransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    @Cacheable(value = "transactions-categories", key = "#userId+'-'+#type")
    public Set<TransactionCategory> execute(Long userId, TransactionTypeEnum type) {
        return transactionCategoryRepository.findAllByTypeAndUserId(userId, type);
    }

}
