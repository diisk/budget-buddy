package br.dev.diisk.application.cases.transaction_category;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.transaction_category.IGetTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;

@Service
public class GetTransactionCategoryCase implements IGetTransactionCategoryCase {

    private final ITransactionCategoryRepository transactionCategoryRepository;

    public GetTransactionCategoryCase(ITransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    public TransactionCategory execute(Long id, User user) {
        Optional<TransactionCategory> category = this.transactionCategoryRepository.findByIdAndUserId(id, user.getId());

        if (category.isEmpty())
            throw new DbValueNotFoundException(TransactionCategory.class, "id");

        return category.get();
    }

}
