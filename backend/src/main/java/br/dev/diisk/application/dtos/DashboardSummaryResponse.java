package br.dev.diisk.application.dtos;

import java.math.BigDecimal;
import java.util.List;

import br.dev.diisk.application.dtos.expense_category.CategoryExpensesDetailsDTO;
import br.dev.diisk.application.dtos.income_category.CategoryIncomesDetailsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DashboardSummaryResponse {
    private BigDecimal totalBalance;
    private BudgetSummaryDTO budgetSummary;
    private List<CategoryExpensesDetailsDTO> expensesByCategory;
    private List<CategoryIncomesDetailsDTO> incomesByCategory;
    private HistoryGraphsDTO historyGraphs;
    private List<LastNotificationDTO> notifications;
    private List<SavingGoalDTO> savingGoals;
}
