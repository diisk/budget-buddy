package br.dev.diisk.application.cases.monthly_history;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.monthly_history.IGetMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.monthly_history.IMonthlyHistoriRepository;
import br.dev.diisk.infra.services.UtilService;
import jakarta.transaction.Transactional;

@Service
public class UpdateMonthlyHistoryCase implements IUpdateMonthlyHistoryCase {

    private UtilService utilService;
    private IListTransactionCase listTransactionCase;
    private IMonthlyHistoriRepository monthlyHistoriRepository;
    private IGetMonthlyHistoryCase getMonthlyHistoryCase;

    public UpdateMonthlyHistoryCase(UtilService utilService, IListTransactionCase listTransactionCase,
            IMonthlyHistoriRepository monthlyHistoriRepository, IGetMonthlyHistoryCase getMonthlyHistoryCase) {
        this.utilService = utilService;
        this.listTransactionCase = listTransactionCase;
        this.monthlyHistoriRepository = monthlyHistoriRepository;
        this.getMonthlyHistoryCase = getMonthlyHistoryCase;
    }

    @Override
    @Transactional
    @CacheEvict(value = "monthly-histories", key = "#user.getId()")
    public MonthlyHistory execute(User user, LocalDateTime referenceDate) {
        MonthlyHistory monthlyHistory = getMonthlyHistoryCase.execute(user.getId(), referenceDate);
        referenceDate = utilService.toReference(referenceDate);

        LocalDateTime endsAt = referenceDate.plusMonths(1).minusSeconds(1);
        Set<Transaction> incomes = listTransactionCase.execute(user.getId(), TransactionTypeEnum.INCOME, referenceDate,
                endsAt);
        Set<Transaction> expenses = listTransactionCase.execute(user.getId(), TransactionTypeEnum.EXPENSE,
                referenceDate, endsAt);

        BigDecimal totalIncomes = incomes.stream().map(income -> income.getValue()).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        BigDecimal totalExpenses = expenses.stream().map(expense -> expense.getValue()).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        BigDecimal netBalance = totalIncomes.subtract(totalExpenses);

        if (monthlyHistory == null) {
            monthlyHistory = new MonthlyHistory();
            monthlyHistory.setReferenceDate(referenceDate);
            monthlyHistory.setUser(user);
        }

        monthlyHistory.setTotalIncomes(totalIncomes);
        monthlyHistory.setTotalExpenses(totalExpenses);
        monthlyHistory.setNetBalance(netBalance);

        return monthlyHistoriRepository.save(monthlyHistory);
    }

}
