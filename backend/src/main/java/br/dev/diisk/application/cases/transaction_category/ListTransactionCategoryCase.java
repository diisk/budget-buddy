package br.dev.diisk.application.cases.transaction_category;

import java.util.Optional;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.exceptions.EnumValueNotFoundException;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;

@Service
public class ListTransactionCategoryCase implements IListTransactionCategoryCase {

    private final ITransactionCategoryRepository transactionCategoryRepository;

    public ListTransactionCategoryCase(ITransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    @Cacheable(value = "transactions-categories", key = "#userId+'-'+#transactionType")
    public Set<TransactionCategory> execute(Long userId, String transactionType) {
        Optional<TransactionTypeEnum> type = TransactionTypeEnum.getByName(transactionType);
        if (type.isEmpty())
            throw new EnumValueNotFoundException(TransactionTypeEnum.class, transactionType);
        return transactionCategoryRepository.findAllByTypeAndUserId(userId, type.get());
    }

}
