package br.dev.diisk.application.interfaces.transaction_category;

import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;

public interface IGetTransactionCategoryCase {
    
    TransactionCategory execute(Long id, User user);
    
}
