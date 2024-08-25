package br.dev.diisk.application.cases.dashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.BalanceDetailsDTO;
import br.dev.diisk.application.dtos.BudgetSummaryDTO;
import br.dev.diisk.application.dtos.HistoryDTO;
import br.dev.diisk.application.dtos.HistoryGraphsDTO;
import br.dev.diisk.application.dtos.NotificationDTO;
import br.dev.diisk.application.dtos.SummaryResponse;
import br.dev.diisk.application.dtos.fund_storage.FundStorageDetailsDTO;
import br.dev.diisk.application.dtos.transaction_category.CategoryTransactionDetailsDTO;
import br.dev.diisk.application.interfaces.dashboard.IGetSummaryCase;
import br.dev.diisk.application.interfaces.fund_storage.IListFundStorageCase;
import br.dev.diisk.application.interfaces.monthly_history.IListMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationCase;
import br.dev.diisk.application.interfaces.notification.IListNotificationCase;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.mappers.notification.NotificationToLastNotificationMapper;
import br.dev.diisk.application.mappers.saving_goal.SavingGoalToDtoMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.infra.services.UtilService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetSummaryCase implements IGetSummaryCase {

    private final IListLastBudgetNotificationCase listLastBudgetNotificationsCase;
    private final IListNotificationCase listNotificationCase;
    private final NotificationToLastNotificationMapper notificationToLastNotificationMapper;
    private final IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private final SavingGoalToDtoMapper savingGoalToDtoMapper;
    private final UtilService utilService;
    private final ModelMapper mapper;
    private final IListMonthlyHistoryCase listMonthlyHistoryCase;
    private final IListFundStorageCase listFundStorageCase;

    @Override
    public SummaryResponse execute(LocalDateTime beginsAt, LocalDateTime endsAt, User user) {
        if (endsAt == null)
            endsAt = LocalDateTime.now();

        SummaryResponse summary = new SummaryResponse();
        List<MonthlyHistory> monthlyHistories = listMonthlyHistoryCase.execute(user.getId(),
                beginsAt != null ? utilService.toReference(beginsAt) : null, utilService.toReference(endsAt));
        Set<BudgetNotification> lastNotifications = listLastBudgetNotificationsCase.execute(user.getId(), endsAt);
        List<Notification> notifications = listNotificationCase.execute(user.getId());
        Set<SavingGoal> activeSavingGoals = listActiveSavingGoalsCase.execute(user.getId(), endsAt);

        List<CategoryTransactionDetailsDTO> categoriesDetails = getCategoriesDetails(monthlyHistories);
        BalanceDetailsDTO details = getBalanceDetails(user);
        BudgetSummaryDTO budgetSummary = getBudgetSummary(monthlyHistories);
        HistoryGraphsDTO historyGraph = getHistoryGraphs(monthlyHistories);

        summary.setIncomesByCategory(
                categoriesDetails.stream().filter(d -> d.getType() == TransactionTypeEnum.INCOME).toList());
        summary.setExpensesByCategory(
                categoriesDetails.stream().filter(d -> d.getType() == TransactionTypeEnum.EXPENSE).toList());
        summary.setBalanceDetails(details);
        summary.setBudgetSummary(budgetSummary);
        summary.setHistoryGraphs(historyGraph);
        summary.setBudgetNotifications(notificationToLastNotificationMapper.mapList(lastNotifications));
        summary.setNotifications(notifications.stream().map(not -> mapper.map(not, NotificationDTO.class)).toList());
        summary.setSavingGoals(savingGoalToDtoMapper.mapList(activeSavingGoals));

        return summary;
    }

    private BalanceDetailsDTO getBalanceDetails(User user) {
        BalanceDetailsDTO balanceDetails = new BalanceDetailsDTO();
        Set<FundStorage> fundStorages = listFundStorageCase.execute(user.getId());
        balanceDetails.setFundStorageBalances(
                fundStorages.stream().map(fs -> mapper.map(fs, FundStorageDetailsDTO.class)).toList());
        balanceDetails.setTotalBalance(
                fundStorages.stream().map(fs -> fs.getBalance()).reduce(BigDecimal.ZERO, BigDecimal::add));
        return balanceDetails;
    }

    private HistoryGraphsDTO getHistoryGraphs(List<MonthlyHistory> monthlyHistories) {
        HistoryGraphsDTO historyGraph = new HistoryGraphsDTO();
        List<HistoryDTO> histories = new ArrayList<>();

        monthlyHistories.forEach(mh -> {
            LocalDateTime rDate = mh.getReferenceDate();
            HistoryDTO history = histories.stream()
                    .filter(h -> h.getYear() == rDate.getYear() && h.getMonth() == (rDate.getMonthValue() + 1))
                    .findFirst().orElse(null);
            if (history == null) {
                history = new HistoryDTO();
                history.setMonth(rDate.getMonthValue() + 1);
                history.setMonthName(utilService.getMonthName(rDate));
                history.setType(mh.getCategory().getType());
                history.setYear(rDate.getYear());
                history.setValue(BigDecimal.ZERO);
                histories.add(history);
            }
            history.setValue(history.getValue().add(mh.getValue()));
        });

        historyGraph
                .setExpenseHistory(histories.stream().filter(h -> h.getType() == TransactionTypeEnum.EXPENSE).toList());
        historyGraph
                .setIncomeHistory(histories.stream().filter(h -> h.getType() == TransactionTypeEnum.INCOME).toList());

        return historyGraph;
    }

    private BudgetSummaryDTO getBudgetSummary(List<MonthlyHistory> monthlyHistories) {
        BudgetSummaryDTO budgetSummary = new BudgetSummaryDTO();

        budgetSummary.setTotalIncome(
                monthlyHistories.stream().filter(mh -> mh.getCategory().getType() == TransactionTypeEnum.INCOME)
                        .map(mh -> mh.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
        budgetSummary.setTotalExpenses(
                monthlyHistories.stream().filter(mh -> mh.getCategory().getType() == TransactionTypeEnum.EXPENSE)
                        .map(mh -> mh.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
        budgetSummary.setNetBalance(budgetSummary.getTotalIncome().subtract(budgetSummary.getTotalExpenses()));

        return budgetSummary;
    }

    private List<CategoryTransactionDetailsDTO> getCategoriesDetails(List<MonthlyHistory> monthlyHistories) {
        List<CategoryTransactionDetailsDTO> details = new ArrayList<>();

        monthlyHistories.forEach(mh -> {
            TransactionCategory category = mh.getCategory();
            CategoryTransactionDetailsDTO detail = details.stream().filter(d -> d.getId() == category.getId())
                    .findFirst().orElse(null);
            if (detail == null) {
                detail = new CategoryTransactionDetailsDTO();
                detail.setType(category.getType());
                detail.setBudgetLimit(category.getBudgetLimit());
                detail.setCategoryName(category.getName());
                detail.setId(category.getId());
                detail.setValue(BigDecimal.ZERO);
                details.add(detail);
            }
            detail.setValue(detail.getValue().add(mh.getValue()));
        });

        details.forEach(
                detail -> detail.setPercentageUsed(utilService.divide(detail.getValue(), detail.getBudgetLimit())));

        return details;
    }

}
