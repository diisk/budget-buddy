package br.dev.diisk.application.interfaces.transaction;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.transaction.Transaction;

public interface IListTransactionByTypeCase {
    
    Set<Transaction> execute(Long userId, String transactionType , LocalDateTime beginsAt, LocalDateTime endsAt);
    
}
