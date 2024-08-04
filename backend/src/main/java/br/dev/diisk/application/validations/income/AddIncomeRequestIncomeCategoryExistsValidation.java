package br.dev.diisk.application.validations.income;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.income.IAddIncomeRequestValidator;
import br.dev.diisk.application.interfaces.income_category.IListIncomesCategoriesCase;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddIncomeRequestIncomeCategoryExistsValidation implements IAddIncomeRequestValidator {

    private IListIncomesCategoriesCase listIncomesCategoriesCase;

    public AddIncomeRequestIncomeCategoryExistsValidation(IListIncomesCategoriesCase listIncomesCategoriesCase) {
        this.listIncomesCategoriesCase = listIncomesCategoriesCase;
    }

    @Override
    public void validate(List<AddIncomeRequest> dtos, User user) {
        Set<IncomeCategory> categories = listIncomesCategoriesCase.execute(user.getId());
        for(AddIncomeRequest dto : dtos){
            if (categories.stream().noneMatch(category -> category.getId()==dto.getCategoryId()))
            throw new DbValueNotFoundException("id", ExpenseCategory.class.getSimpleName());
        }
    }


}
