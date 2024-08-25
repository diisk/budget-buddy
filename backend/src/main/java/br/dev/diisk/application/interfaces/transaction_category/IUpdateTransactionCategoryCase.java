package br.dev.diisk.application.interfaces.transaction_category;

import br.dev.diisk.application.dtos.transaction_category.UpdateTransactionCategoryRequest;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateTransactionCategoryCase {

    TransactionCategory execute(Long id, UpdateTransactionCategoryRequest dto, User owner);

}
