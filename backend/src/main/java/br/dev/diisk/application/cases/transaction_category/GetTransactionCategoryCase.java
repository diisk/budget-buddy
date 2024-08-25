package br.dev.diisk.application.cases.transaction_category;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.FindByIdAndUserCase;
import br.dev.diisk.application.interfaces.transaction_category.IGetTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;

@Service
public class GetTransactionCategoryCase extends FindByIdAndUserCase<TransactionCategory>
        implements IGetTransactionCategoryCase {

    public GetTransactionCategoryCase(ITransactionCategoryRepository repository) {
        super(repository);
    }


}
