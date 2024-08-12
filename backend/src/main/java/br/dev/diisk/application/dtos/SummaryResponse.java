package br.dev.diisk.application.dtos;

import java.util.List;

import br.dev.diisk.application.dtos.expense_category.CategoryExpensesDetailsDTO;
import br.dev.diisk.application.dtos.income_category.CategoryIncomesDetailsDTO;
import br.dev.diisk.application.dtos.saving_goal.SavingGoalDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SummaryResponse {
    private BalanceDetailsDTO balanceDetails;
    private BudgetSummaryDTO budgetSummary;
    private List<CategoryExpensesDetailsDTO> expensesByCategory;
    private List<CategoryIncomesDetailsDTO> incomesByCategory;
    private HistoryGraphsDTO historyGraphs;
    private List<NotificationDTO> notifications;
    private List<LastBudgetNotificationDTO> budgetNotifications;
    private List<SavingGoalDTO> savingGoals;
}
