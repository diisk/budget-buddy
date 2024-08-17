package br.dev.diisk.application.cases.transaction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.transaction.IListTransactionByTypeCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

@Service
public class ListTransactionByTypeCase implements IListTransactionByTypeCase {

    private final IListTransactionCase listTransactionCase;
    private final IListTransactionCategoryCase listTransactionCategoryCase;

    public ListTransactionByTypeCase(IListTransactionCase listTransactionCase,
            IListTransactionCategoryCase listTransactionCategoryCase) {
        this.listTransactionCase = listTransactionCase;
        this.listTransactionCategoryCase = listTransactionCategoryCase;
    }

    @Override
    public Set<Transaction> execute(Long userId, TransactionTypeEnum type, LocalDateTime beginsAt,
            LocalDateTime endsAt) {
        Set<TransactionCategory> categories = listTransactionCategoryCase.execute(userId, type);
        Set<Transaction> transactions = new HashSet<>();
        for (TransactionCategory category : categories) {
            transactions.addAll(listTransactionCase.execute(userId, category, beginsAt, endsAt));
        }
        return transactions;
    }

}
