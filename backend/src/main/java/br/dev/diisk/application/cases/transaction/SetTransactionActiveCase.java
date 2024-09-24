package br.dev.diisk.application.cases.transaction;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.SetEntityActiveCase;
import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.application.interfaces.fund_storage.IRegisterTransactionValueCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.transaction.IGetTransactionCase;
import br.dev.diisk.application.interfaces.transaction.ISetTransactionActiveCase;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.application.interfaces.cache.ICacheService;
import jakarta.transaction.Transactional;

@Service
public class SetTransactionActiveCase extends SetEntityActiveCase<Transaction> implements ISetTransactionActiveCase {

    private final ITransactionRepository transactionRepository;
    private final IGetTransactionCase getTransactionCase;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final ICacheService cacheService;
    private final IRegisterTransactionValueCase registerTransactionValueCase;

    public SetTransactionActiveCase(ITransactionRepository repository, ITransactionRepository transactionRepository,
            IGetTransactionCase getTransactionCase, IUpdateMonthlyHistoryCase updateMonthlyHistoryCase,
            ICacheService cacheService, IRegisterTransactionValueCase registerTransactionValueCase) {
        super(repository);
        this.transactionRepository = transactionRepository;
        this.getTransactionCase = getTransactionCase;
        this.updateMonthlyHistoryCase = updateMonthlyHistoryCase;
        this.cacheService = cacheService;
        this.registerTransactionValueCase = registerTransactionValueCase;
    }

    @Override
    @Transactional
    public void execute(Long id, Boolean active, User owner) {
        super.execute(id, active, owner);

        Transaction transaction = getTransactionCase.execute(id, owner);
        cacheService.evictCache("transactions", owner.getId().toString() + "-" + transaction.getCategory().getId());

        FundStorage fundStorage = transaction.getFundStorage();

        registerTransactionValueCase.execute(fundStorage.getId(), new TransactionValueDTO(
                transaction.getValue(),
                transaction.getCategory().getType(),
                true), owner);

        transactionRepository.save(transaction);

        updateMonthlyHistoryCase.execute(owner, transaction.getDate(), transaction.getCategory());

    }

}
