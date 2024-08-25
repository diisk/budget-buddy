package br.dev.diisk.application.cases.transaction;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListTransactionCase implements IListTransactionCase {

    private final ITransactionRepository transactionRepository;

    @Override
    @Cacheable(value = "transactions", key = "#userId+'-'+#category.getId()+'-'+#beginsAt+'-'+#endsAt")
    public Set<Transaction> execute(Long userId, TransactionCategory category, LocalDateTime beginsAt,
            LocalDateTime endsAt) {
        if (endsAt == null)
            endsAt = LocalDateTime.now();
        return transactionRepository.findAllInPeriod(userId, category.getId(), beginsAt, endsAt);
    }

}
