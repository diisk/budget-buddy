package br.dev.diisk.application.cases.dashboard;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
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
import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationCase;
import br.dev.diisk.application.interfaces.notification.IListNotificationCase;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionByTypeCase;
import br.dev.diisk.application.interfaces.transaction.IListTransactionCase;
import br.dev.diisk.application.interfaces.transaction_category.IListTransactionCategoryCase;
import br.dev.diisk.application.mappers.notification.NotificationToLastNotificationMapper;
import br.dev.diisk.application.mappers.saving_goal.SavingGoalToDtoMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.infra.services.UtilService;

@Service
public class GetSummaryCase implements IGetSummaryCase {

    private final IListTransactionByTypeCase listTransactionByTypeCase;
    private final IListLastBudgetNotificationCase listLastBudgetNotificationsCase;
    private final IListNotificationCase listNotificationCase;
    private final NotificationToLastNotificationMapper notificationToLastNotificationMapper;
    private final IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private final SavingGoalToDtoMapper savingGoalToDtoMapper;
    private final IListTransactionCategoryCase listTransactionCategoryCase;
    private final UtilService utilService;
    private final ModelMapper mapper;

    public GetSummaryCase(IListTransactionByTypeCase listTransactionByTypeCase,
            IListLastBudgetNotificationCase listLastBudgetNotificationsCase, IListNotificationCase listNotificationCase,
            NotificationToLastNotificationMapper notificationToLastNotificationMapper,
            IListActiveSavingGoalsCase listActiveSavingGoalsCase, SavingGoalToDtoMapper savingGoalToDtoMapper,
            IListTransactionCategoryCase listTransactionCategoryCase, UtilService utilService, ModelMapper mapper) {
        this.listTransactionByTypeCase = listTransactionByTypeCase;
        this.listLastBudgetNotificationsCase = listLastBudgetNotificationsCase;
        this.listNotificationCase = listNotificationCase;
        this.notificationToLastNotificationMapper = notificationToLastNotificationMapper;
        this.listActiveSavingGoalsCase = listActiveSavingGoalsCase;
        this.savingGoalToDtoMapper = savingGoalToDtoMapper;
        this.listTransactionCategoryCase = listTransactionCategoryCase;
        this.utilService = utilService;
        this.mapper = mapper;
    }

    @Override
    public SummaryResponse execute(LocalDateTime beginsAt, LocalDateTime endsAt, User user) {
        SummaryResponse summary = new SummaryResponse();

        if (endsAt == null)
            endsAt = LocalDateTime.now();
        Set<Transaction> incomes = listTransactionByTypeCase.execute(user.getId(), TransactionTypeEnum.INCOME, beginsAt,
                endsAt);
        Set<Transaction> expenses = listTransactionByTypeCase.execute(user.getId(), TransactionTypeEnum.EXPENSE,
                beginsAt, endsAt);

        Set<BudgetNotification> lastNotifications = listLastBudgetNotificationsCase.execute(user.getId(), endsAt);
        List<Notification> notifications = listNotificationCase.execute(user.getId());
        Set<SavingGoal> activeSavingGoals = listActiveSavingGoalsCase.execute(user.getId(), endsAt);

        summary.setIncomesByCategory(getIncomesByCategory(incomes));
        summary.setExpensesByCategory(getExpensesByCategory(expenses));

        summary.setBalanceDetails(getBalanceDetails(incomes, expenses));
        summary.setBudgetSummary(getBudgetSummary(incomes, expenses));// History
        summary.setHistoryGraphs(getHistoryGraphs(incomes, expenses));// HISTORY

        summary.setBudgetNotifications(notificationToLastNotificationMapper.mapList(lastNotifications));
        summary.setNotifications(notifications.stream().map(not -> mapper.map(not, NotificationDTO.class)).toList());
        summary.setSavingGoals(savingGoalToDtoMapper.mapList(activeSavingGoals));

        return summary;
    }

    private HistoryGraphsDTO getHistoryGraphs(Collection<Transaction> incomes, Collection<Transaction> expenses) {
        HistoryGraphsDTO history = new HistoryGraphsDTO();

        history.setExpenseHistory(new ArrayList<>());
        history.setIncomeHistory(new ArrayList<>());

        for (Transaction income : incomes) {
            LocalDateTime date = income.getDate();
            HistoryDTO dto = history.getIncomeHistory().stream()
                    .filter(his -> his.getMonth() == date.getMonthValue() && his.getYear() == date.getYear())
                    .findFirst().orElse(null);
            if (dto == null) {
                dto = new HistoryDTO();
                dto.setValue(BigDecimal.ZERO);
                dto.setMonthName(utilService.getMonthName(date));
                dto.setMonth(date.getMonthValue());
                dto.setYear(date.getYear());
                history.getIncomeHistory().add(dto);
            }
            dto.setValue(dto.getValue().add(income.getValue()));
        }

        for (Transaction expense : expenses) {
            LocalDateTime date = expense.getDate();
            HistoryDTO dto = history.getExpenseHistory().stream()
                    .filter(his -> his.getMonth() == date.getMonthValue() && his.getYear() == date.getYear())
                    .findFirst().orElse(null);
            if (dto == null) {
                dto = new HistoryDTO();
                dto.setValue(BigDecimal.ZERO);
                dto.setMonth(date.getMonthValue());
                dto.setYear(date.getYear());
                history.getExpenseHistory().add(dto);
            }
            dto.setValue(dto.getValue().add(expense.getValue()));
        }

        return history;
    }

    private List<CategoryTransactionDetailsDTO> getExpensesByCategory(Collection<Transaction> expenses) {
        List<CategoryTransactionDetailsDTO> list = new ArrayList<>();

        for (Transaction expense : expenses) {
            TransactionCategory category = expense.getCategory();
            CategoryTransactionDetailsDTO catDetails = list.stream()
                    .filter(cat -> cat.getId() == category.getId()).findFirst().orElse(null);
            if (catDetails == null) {
                catDetails = new CategoryTransactionDetailsDTO();
                catDetails.setId(category.getId());
                catDetails.setBudgetLimit(category.getBudgetLimit());
                catDetails.setCategoryName(category.getName());
                catDetails.setValue(BigDecimal.ZERO);
                list.add(catDetails);
            }
            catDetails.setValue(catDetails.getValue().add(expense.getValue()));
        }

        for (CategoryTransactionDetailsDTO cat : list) {
            cat.setPercentageUsed(utilService.divide(cat.getValue(), cat.getBudgetLimit()));
        }

        return list;
    }

    private List<CategoryTransactionDetailsDTO> getIncomesByCategory(Collection<Transaction> incomes) {
        List<CategoryTransactionDetailsDTO> list = new ArrayList<>();

        for (Transaction income : incomes) {
            TransactionCategory category = income.getCategory();
            CategoryTransactionDetailsDTO catDetails = list.stream()
                    .filter(cat -> cat.getId() == category.getId()).findFirst().orElse(null);
            if (catDetails == null) {
                catDetails = new CategoryTransactionDetailsDTO();
                catDetails.setId(category.getId());
                catDetails.setCategoryName(category.getName());
                catDetails.setValue(BigDecimal.ZERO);
                list.add(catDetails);
            }
            catDetails.setValue(catDetails.getValue().add(income.getValue()));
        }

        return list;
    }

    private BudgetSummaryDTO getBudgetSummary(Collection<Transaction> incomes, Collection<Transaction> expenses) {
        BudgetSummaryDTO budgetSummary = new BudgetSummaryDTO();
        BigDecimal totalIncome = incomes
                .stream().map(inc -> inc.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpenses = expenses
                .stream().map(exp -> exp.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);

        budgetSummary.setTotalIncome(totalIncome);
        budgetSummary.setTotalExpenses(totalExpenses);
        budgetSummary.setNetBalance(totalIncome.subtract(totalExpenses));

        return budgetSummary;
    }

    private BalanceDetailsDTO getBalanceDetails(Collection<Transaction> incomes, Collection<Transaction> expenses) {
        BalanceDetailsDTO details = new BalanceDetailsDTO();

        details.setTotalBalance(BigDecimal.ZERO);
        details.setFundStorageBalances(new ArrayList<FundStorageDetailsDTO>());

        for (Transaction income : incomes) {
            FundStorage fundStorage = income.getStorage();
            details.setTotalBalance(details.getTotalBalance().add(income.getValue()));
            FundStorageDetailsDTO fundDetails = details.getFundStorageBalances()
                    .stream().filter(fund -> fund.getId() == income.getStorage().getId())
                    .findFirst().orElse(null);
            if (fundDetails == null) {
                fundDetails = new FundStorageDetailsDTO();
                fundDetails.setName(fundStorage.getName());
                fundDetails.setBalance(BigDecimal.ZERO);
                fundDetails.setCreditCard(fundStorage.getCreditCard());
                fundDetails.setId(fundStorage.getId());
                details.getFundStorageBalances().add(fundDetails);
            }
            fundDetails.setBalance(fundDetails.getBalance().add(income.getValue()));
        }

        for (Transaction expense : expenses) {
            details.setTotalBalance(details.getTotalBalance().subtract(expense.getValue()));
            FundStorageDetailsDTO fundDetails = details.getFundStorageBalances()
                    .stream().filter(fund -> fund.getId() == expense.getStorage().getId())
                    .findFirst().orElse(null);
            if (fundDetails == null) {
                fundDetails = new FundStorageDetailsDTO();
                fundDetails.setBalance(BigDecimal.ZERO);
                fundDetails.setCreditCard(expense.getStorage().getCreditCard());
                fundDetails.setId(expense.getStorage().getId());
                details.getFundStorageBalances().add(fundDetails);
            }
            fundDetails.setBalance(fundDetails.getBalance().subtract(expense.getValue()));
        }

        return details;
    }

}
