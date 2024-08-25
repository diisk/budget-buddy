package br.dev.diisk.application.cases.transaction;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.FindByIdAndUserCase;
import br.dev.diisk.application.interfaces.transaction.IGetTransactionCase;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;

@Service
public class GetTransactionCase extends FindByIdAndUserCase<Transaction> implements IGetTransactionCase {

    public GetTransactionCase(ITransactionRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

}
