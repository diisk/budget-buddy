package br.dev.diisk.application.cases.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.BalanceDetailsDTO;
import br.dev.diisk.application.dtos.BudgetSummaryDTO;
import br.dev.diisk.application.dtos.HistoryDTO;
import br.dev.diisk.application.dtos.HistoryGraphsDTO;
import br.dev.diisk.application.dtos.SummaryResponse;
import br.dev.diisk.application.dtos.expense_category.CategoryExpensesDetailsDTO;
import br.dev.diisk.application.dtos.fund_storage.FundStorageDetailsDTO;
import br.dev.diisk.application.dtos.income_category.CategoryIncomesDetailsDTO;
import br.dev.diisk.application.interfaces.dashboard.IGetSummaryCase;
import br.dev.diisk.application.interfaces.expense.IListExpensesCase;
import br.dev.diisk.application.interfaces.income.IListIncomesCase;
import br.dev.diisk.application.interfaces.notification.IListLastNotificationsCase;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.mappers.notification.NotificationToLastNotificationMapper;
import br.dev.diisk.application.mappers.saving_goal.SavingGoalToDtoMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.entities.user.User;

@Service
public class GetSummaryCase implements IGetSummaryCase {

    private IListIncomesCase listIncomesCase;
    private IListExpensesCase listExpensesCase;
    private IListLastNotificationsCase listLastNotificationsCase;
    private NotificationToLastNotificationMapper notificationToLastNotificationMapper;
    private IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private SavingGoalToDtoMapper savingGoalToDtoMapper;

    public GetSummaryCase(IListIncomesCase listIncomesCase, IListExpensesCase listExpensesCase,
            IListLastNotificationsCase listLastNotificationsCase,
            NotificationToLastNotificationMapper notificationToLastNotificationMapper,
            IListActiveSavingGoalsCase listActiveSavingGoalsCase, SavingGoalToDtoMapper savingGoalToDtoMapper) {
        this.listIncomesCase = listIncomesCase;
        this.listExpensesCase = listExpensesCase;
        this.listLastNotificationsCase = listLastNotificationsCase;
        this.notificationToLastNotificationMapper = notificationToLastNotificationMapper;
        this.listActiveSavingGoalsCase = listActiveSavingGoalsCase;
        this.savingGoalToDtoMapper = savingGoalToDtoMapper;
    }

    @Override
    public SummaryResponse execute(LocalDateTime beginsAt, LocalDateTime endsAt, User user) {
        SummaryResponse summary = new SummaryResponse();

        if (endsAt == null)
            endsAt = LocalDateTime.now();
        Set<Income> incomes = listIncomesCase.execute(user.getId(), beginsAt, endsAt);
        Set<Expense> expenses = listExpensesCase.execute(user.getId(), beginsAt, endsAt);
        Set<Notification> lastNotifications = listLastNotificationsCase.execute(user.getId(), endsAt);
        Set<SavingGoal> activeSavingGoals = listActiveSavingGoalsCase.execute(user.getId(), endsAt);

        summary.setBalanceDetails(getBalanceDetails(incomes, expenses));
        summary.setBudgetSummary(getBudgetSummary(incomes, expenses));
        summary.setIncomesByCategory(getIncomesByCategory(incomes));
        summary.setExpensesByCategory(getExpensesByCategory(expenses));
        summary.setHistoryGraphs(getHistoryGraphs(incomes, expenses));
        summary.setNotifications(notificationToLastNotificationMapper.mapList(lastNotifications));
        summary.setSavingGoals(savingGoalToDtoMapper.mapList(activeSavingGoals));

        return summary;
    }

    private HistoryGraphsDTO getHistoryGraphs(Collection<Income> incomes, Collection<Expense> expenses) {
        HistoryGraphsDTO history = new HistoryGraphsDTO();

        history.setExpenseHistory(new ArrayList<>());
        history.setIncomeHistory(new ArrayList<>());

        for (Income income : incomes) {
            LocalDateTime date = income.getDate();
            HistoryDTO dto = history.getIncomeHistory().stream()
                    .filter(his -> his.getMonth() == date.getMonthValue() && his.getYear() == date.getYear())
                    .findFirst().orElse(null);
            if (dto == null) {
                dto = new HistoryDTO();
                dto.setValue(BigDecimal.ZERO);
                dto.setMonth(date.getMonthValue());
                dto.setYear(date.getYear());
                history.getIncomeHistory().add(dto);
            }
            dto.setValue(dto.getValue().add(income.getValue()));
        }

        for (Expense expense : expenses) {
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

    private List<CategoryExpensesDetailsDTO> getExpensesByCategory(Collection<Expense> expenses) {
        List<CategoryExpensesDetailsDTO> list = new ArrayList<>();

        for (Expense expense : expenses) {
            ExpenseCategory category = expense.getCategory();
            CategoryExpensesDetailsDTO catDetails = list.stream()
                    .filter(cat -> cat.getId() == category.getId()).findFirst().orElse(null);
            if (catDetails == null) {
                catDetails = new CategoryExpensesDetailsDTO();
                catDetails.setId(category.getId());
                catDetails.setBudgetLimit(category.getBudgetLimit());
                catDetails.setCategoryName(category.getName());
                catDetails.setValue(BigDecimal.ZERO);
                list.add(catDetails);
            }
            catDetails.setValue(catDetails.getValue().add(expense.getValue()));
        }

        for(CategoryExpensesDetailsDTO cat:list){
            cat.setPercentageUsed(cat.getValue().divide(cat.getBudgetLimit(),2,RoundingMode.HALF_EVEN));
        }

        return list;
    }

    private List<CategoryIncomesDetailsDTO> getIncomesByCategory(Collection<Income> incomes) {
        List<CategoryIncomesDetailsDTO> list = new ArrayList<>();

        for (Income income : incomes) {
            IncomeCategory category = income.getCategory();
            CategoryIncomesDetailsDTO catDetails = list.stream()
                    .filter(cat -> cat.getId() == category.getId()).findFirst().orElse(null);
            if (catDetails == null) {
                catDetails = new CategoryIncomesDetailsDTO();
                catDetails.setId(category.getId());
                catDetails.setCategoryName(category.getName());
                catDetails.setValue(BigDecimal.ZERO);
                list.add(catDetails);
            }
            catDetails.setValue(catDetails.getValue().add(income.getValue()));
        }

        return list;
    }

    private BudgetSummaryDTO getBudgetSummary(Collection<Income> incomes, Collection<Expense> expenses) {
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

    private BalanceDetailsDTO getBalanceDetails(Collection<Income> incomes, Collection<Expense> expenses) {
        BalanceDetailsDTO details = new BalanceDetailsDTO();

        details.setTotalBalance(BigDecimal.ZERO);
        details.setFundStorageBalances(new ArrayList<FundStorageDetailsDTO>());

        for (Income income : incomes) {
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

        for (Expense expense : expenses) {
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
