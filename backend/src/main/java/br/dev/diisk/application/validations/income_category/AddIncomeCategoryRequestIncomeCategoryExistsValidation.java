package br.dev.diisk.application.validations.income_category;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income_category.AddIncomeCategoryRequest;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.income_category.IAddIncomeCategoryRequestValidator;
import br.dev.diisk.application.interfaces.income_category.IListIncomesCategoriesCase;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddIncomeCategoryRequestIncomeCategoryExistsValidation implements IAddIncomeCategoryRequestValidator {

    private IListIncomesCategoriesCase listIncomesCategoriesCase;

    public AddIncomeCategoryRequestIncomeCategoryExistsValidation(IListIncomesCategoriesCase listIncomesCategoriesCase) {
        this.listIncomesCategoriesCase = listIncomesCategoriesCase;
    }

    @Override
    public void validate(AddIncomeCategoryRequest dto, User user) {
        Set<IncomeCategory> categories = listIncomesCategoriesCase.execute(user.getId());
        if (categories.stream().anyMatch(category -> category.getName().equalsIgnoreCase(dto.getName())))
            throw new ValueAlreadyInDatabaseException("name", IncomeCategory.class.getSimpleName());
    }

}
