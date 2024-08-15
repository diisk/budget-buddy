package br.dev.diisk.application.dtos;

import java.util.List;

import br.dev.diisk.application.dtos.saving_goal.SavingGoalDTO;
import br.dev.diisk.application.dtos.transaction_category.CategoryTransactionDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SummaryResponse {
    private BalanceDetailsDTO balanceDetails;
    private BudgetSummaryDTO budgetSummary;
    private List<CategoryTransactionDetailsDTO> expensesByCategory;
    private List<CategoryTransactionDetailsDTO> incomesByCategory;
    private HistoryGraphsDTO historyGraphs;
    private List<NotificationDTO> notifications;
    private List<LastBudgetNotificationDTO> budgetNotifications;
    private List<SavingGoalDTO> savingGoals;
}
