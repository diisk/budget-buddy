package br.dev.diisk.application.interfaces.transaction_category;

import java.util.Set;

import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

public interface IListTransactionCategoryCase {
    
    Set<TransactionCategory> execute(Long userId, TransactionTypeEnum type);
    
}
