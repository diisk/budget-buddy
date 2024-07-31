package br.dev.diisk.application.cases.expense;

import java.util.Set;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.expense.IListExpensesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.repositories.expense.IExpenseCategoryRepository;

@Service
public class ListExpensesCategoriesCase implements IListExpensesCategoriesCase{

    private IExpenseCategoryRepository expenseCategoryRepository;

    public ListExpensesCategoriesCase(IExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    @Override
    public Set<ExpenseCategory> execute(Long userId) {
        return expenseCategoryRepository.findAllByUserId(userId);
    }

}
