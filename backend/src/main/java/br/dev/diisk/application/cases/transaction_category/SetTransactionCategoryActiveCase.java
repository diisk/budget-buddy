package br.dev.diisk.application.cases.transaction_category;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.SetEntityActiveCase;
import br.dev.diisk.application.interfaces.transaction.ISetTransactionActiveCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class SetTransactionCategoryActiveCase extends SetEntityActiveCase<TransactionCategory>
        implements ISetTransactionActiveCase {

    private final CacheService cacheService;

    public SetTransactionCategoryActiveCase(ITransactionCategoryRepository repository,
            CacheService cacheService) {
        super(repository);
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public void execute(Long id, Boolean active, User user) {
        cacheService.evictCache("transactions-categories", user.getId().toString());
        super.execute(id, active, user);
    }

}
