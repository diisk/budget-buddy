package br.dev.diisk.application.cases.monthly_history;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.monthly_history.IGetMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.monthly_history.IMonthlyHistoriRepository;
import br.dev.diisk.infra.services.CacheService;
import br.dev.diisk.infra.services.UtilService;
import jakarta.transaction.Transactional;

@Service
public class UpdateMonthlyHistoryCase implements IUpdateMonthlyHistoryCase {

    private final UtilService utilService;
    private final IListTransactionCase listTransactionCase;
    private final IMonthlyHistoriRepository monthlyHistoriRepository;
    private final IGetMonthlyHistoryCase getMonthlyHistoryCase;
    private final CacheService cacheService;

    public UpdateMonthlyHistoryCase(UtilService utilService, IListTransactionCase listTransactionCase,
            IMonthlyHistoriRepository monthlyHistoriRepository, IGetMonthlyHistoryCase getMonthlyHistoryCase,
            CacheService cacheService) {
        this.utilService = utilService;
        this.listTransactionCase = listTransactionCase;
        this.monthlyHistoriRepository = monthlyHistoriRepository;
        this.getMonthlyHistoryCase = getMonthlyHistoryCase;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public MonthlyHistory execute(User user, LocalDateTime referenceDate, TransactionCategory category) {
        MonthlyHistory monthlyHistory = getMonthlyHistoryCase.execute(user.getId(), referenceDate, category);
        referenceDate = utilService.toReference(referenceDate);

        cacheService.evictCache("monthly-histories", user.getId().toString());

        if (monthlyHistory == null) {
            monthlyHistory = new MonthlyHistory();
            monthlyHistory.setCategory(category);
            monthlyHistory.setReferenceDate(referenceDate);
            monthlyHistory.setUser(user);
        }

        LocalDateTime endsAt = referenceDate.plusMonths(1).minusSeconds(1);
        Set<Transaction> transactions = listTransactionCase.execute(user.getId(), category,
                referenceDate, endsAt);
        BigDecimal monthlyValue = transactions.stream().map(transaction -> transaction.getValue()).reduce(
                BigDecimal.ZERO,
                BigDecimal::add);

        monthlyHistory.setValue(monthlyValue);

        return monthlyHistoriRepository.save(monthlyHistory);
    }

}
