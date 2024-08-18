package br.dev.diisk.application.interfaces.transaction_category;

import java.util.Set;

import br.dev.diisk.domain.entities.transaction.TransactionCategory;

public interface IListTransactionCategoryCase {
    
    Set<TransactionCategory> execute(Long userId, String transactionType);
    
}
