package br.dev.diisk.application.cases.expense_category;

import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.expense_category.IListExpensesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;

@Service
public class ListExpensesCategoriesCase implements IListExpensesCategoriesCase{

    private IExpenseCategoryRepository expenseCategoryRepository;

    public ListExpensesCategoriesCase(IExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    @Cacheable(value = "expenses-categories", key = "#userId")
    public Set<ExpenseCategory> execute(Long userId) {
        return expenseCategoryRepository.findAllByUserId(userId);
    }

}
