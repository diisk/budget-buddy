package br.dev.diisk.application.cases.transaction;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;

@Service
public class ListTransactionCase implements IListTransactionCase {

    private final ITransactionRepository transactionRepository;

    public ListTransactionCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Cacheable(value = "transactions", key = "#userId+'-'+type+'-'+#beginsAt+'-'+#endsAt")
    public Set<Transaction> execute(Long userId, TransactionTypeEnum type, LocalDateTime beginsAt,
            LocalDateTime endsAt) {
        if (endsAt == null)
            endsAt = LocalDateTime.now();
        return transactionRepository.findAllInPeriod(userId, type, beginsAt, endsAt);
    }

}
