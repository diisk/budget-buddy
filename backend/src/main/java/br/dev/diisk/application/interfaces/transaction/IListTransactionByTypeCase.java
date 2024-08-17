package br.dev.diisk.application.interfaces.transaction;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

public interface IListTransactionByTypeCase {
    
    Set<Transaction> execute(Long userId, TransactionTypeEnum type , LocalDateTime beginsAt, LocalDateTime endsAt);
    
}
