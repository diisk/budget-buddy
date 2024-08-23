package br.dev.diisk.application.cases.transaction;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.transaction.IGetTransactionCase;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;

@Service
public class GetTransactionCase implements IGetTransactionCase {

    private final ITransactionRepository transactionRepository;

    public GetTransactionCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction execute(Long id, User owner) {
        Optional<Transaction> result = this.transactionRepository.findByIdAndUserId(id, owner.getId());
        if (result.isEmpty())
            throw new DbValueNotFoundException(Transaction.class, "id");

        return result.get();
    }

}
