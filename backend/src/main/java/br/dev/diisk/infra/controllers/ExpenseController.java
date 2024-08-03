package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCategoryCase;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCase;
import br.dev.diisk.application.interfaces.expense.IListExpensesCategoriesCase;
import br.dev.diisk.application.interfaces.expense.IListExpensesCase;
import br.dev.diisk.application.interfaces.expense.IUpdateExpenseCase;
import br.dev.diisk.application.mappers.expense.ExpenseCategoryToResponseMapper;
import br.dev.diisk.application.mappers.expense.ExpenseToResponseMapper;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.dtos.expense.AddExpenseCategoryRequest;
import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.dtos.expense.ExpenseCategoryResponse;
import br.dev.diisk.application.dtos.expense.ExpenseResponse;
import br.dev.diisk.application.dtos.expense.UpdateExpenseRequest;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("expenses")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class ExpenseController {

    private ExpenseCategoryToResponseMapper expenseCategoryToResponseMapper;
    private ExpenseToResponseMapper expenseToResponseMapper;
    private IAddExpenseCase addExpenseCase;
    private IUpdateExpenseCase updateExpenseCase;
    private IAddExpenseCategoryCase addExpenseCategoryCase;
    private IResponseService responseService;
    private IListExpensesCategoriesCase listExpensesCategoriesCase;
    private IListExpensesCase listExpensesCase;

    public ExpenseController(ExpenseCategoryToResponseMapper expenseCategoryToResponseMapper,
            ExpenseToResponseMapper expenseToResponseMapper, IAddExpenseCase addExpenseCase,
            IUpdateExpenseCase updateExpenseCase, IAddExpenseCategoryCase addCategoryCase,
            IResponseService responseService, IListExpensesCategoriesCase listCategoriesCase,
            IListExpensesCase listExpensesCase) {
        this.expenseCategoryToResponseMapper = expenseCategoryToResponseMapper;
        this.expenseToResponseMapper = expenseToResponseMapper;
        this.addExpenseCase = addExpenseCase;
        this.updateExpenseCase = updateExpenseCase;
        this.addExpenseCategoryCase = addCategoryCase;
        this.responseService = responseService;
        this.listExpensesCategoriesCase = listCategoriesCase;
        this.listExpensesCase = listExpensesCase;
    }

    @PostMapping("categories")
    public ResponseEntity<GenericResponse<ExpenseCategoryResponse>> addCategory(
            @RequestBody AddExpenseCategoryRequest dto, @AuthenticationPrincipal User user) {
        ExpenseCategory expenseCategory = addExpenseCategoryCase.execute(dto, user);
        ExpenseCategoryResponse response = expenseCategoryToResponseMapper.apply(expenseCategory);
        return responseService.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<GenericResponse<ExpenseResponse>> updateExpense(@PathVariable Long id,
            @RequestBody @Valid UpdateExpenseRequest dto,
            @AuthenticationPrincipal User user) {
        Expense expense = updateExpenseCase.execute(id, dto, user);
        ExpenseResponse response = expenseToResponseMapper.apply(expense);
        return responseService.ok(response);
    }

    @PostMapping
    public ResponseEntity<GenericResponse<ExpenseResponse>> addExpense(@RequestBody @Valid AddExpenseRequest dto,
            @AuthenticationPrincipal User user) {
        Expense expense = addExpenseCase.execute(dto, user);
        ExpenseResponse response = expenseToResponseMapper.apply(expense);
        return responseService.ok(response);
    }

    @GetMapping("categories")
    public ResponseEntity<GenericResponse<List<ExpenseCategoryResponse>>> listCategories(
            @AuthenticationPrincipal User user) {
        Set<ExpenseCategory> categories = listExpensesCategoriesCase.execute(user.getId());
        List<ExpenseCategoryResponse> response = expenseCategoryToResponseMapper.mapList(categories);
        return responseService.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse<List<ExpenseResponse>>> listExpenses(
            @RequestParam LocalDateTime beginsAt,
            @RequestParam LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {
        Set<Expense> expenses = listExpensesCase.execute(user.getId(),
                beginsAt, endsAt);
        List<ExpenseResponse> response = expenseToResponseMapper.mapList(expenses);
        return responseService.ok(response);
    }

}
