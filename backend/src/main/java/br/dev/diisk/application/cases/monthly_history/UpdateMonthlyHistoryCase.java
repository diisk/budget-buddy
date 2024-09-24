package br.dev.diisk.application.cases.monthly_history;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.UtilService;
import br.dev.diisk.application.interfaces.cache.ICacheService;
import br.dev.diisk.application.interfaces.monthly_history.IListMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.notification.IAddNotificationCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.monthly_history.IMonthlyHistoriRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMonthlyHistoryCase implements IUpdateMonthlyHistoryCase {

    private final UtilService utilService;
    private final IListTransactionCase listTransactionCase;
    private final IMonthlyHistoriRepository monthlyHistoriRepository;
    private final ICacheService cacheService;
    private final IListMonthlyHistoryCase listMonthlyHistoryCase;
    private final IAddNotificationCase addNotificationCase;

    @Override
    @Transactional
    public MonthlyHistory execute(User user, LocalDateTime referenceDate, TransactionCategory category) {
        final LocalDateTime monthlyReferenceDate = utilService.toReference(referenceDate);
        List<MonthlyHistory> monthlyHistories = listMonthlyHistoryCase.execute(user.getId(), monthlyReferenceDate,
                monthlyReferenceDate);
        MonthlyHistory monthlyHistory = monthlyHistories.stream()
                .filter(mh -> mh.getCategory() == category && mh.getReferenceDate() == monthlyReferenceDate).findFirst()
                .orElse(null);

        BigDecimal monthBalance = getMonthBalance(monthlyHistories);

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
        monthlyHistoriRepository.save(monthlyHistory);

        if (monthBalance.compareTo(BigDecimal.ZERO) > 0) {
            monthlyHistories = listMonthlyHistoryCase.execute(user.getId(), monthlyReferenceDate,
                    monthlyReferenceDate);
            monthBalance = getMonthBalance(monthlyHistories);
            if (monthBalance.compareTo(BigDecimal.ZERO) < 0) {
                addNotificationCase.execute("O saldo mensal referente (%s) está negativo."
                        .formatted(referenceDate.toLocalDate().toString()), user);
            }
        }

        return monthlyHistory;
    }

    private BigDecimal getMonthBalance(List<MonthlyHistory> monthlyHistories) {
        BigDecimal balance = monthlyHistories.stream()
                .filter(mh -> mh.getCategory().getType() == TransactionTypeEnum.INCOME).map(mh -> mh.getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        balance = monthlyHistories.stream()
                .filter(mh -> mh.getCategory().getType() == TransactionTypeEnum.EXPENSE).map(mh -> mh.getValue())
                .reduce(balance, BigDecimal::subtract);
        return balance;
    }

}
