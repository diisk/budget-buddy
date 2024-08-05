package br.dev.diisk.application.validations.income_category;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income_category.AddIncomeCategoryRequest;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.income_category.IAddIncomeCategoryRequestValidator;
import br.dev.diisk.application.interfaces.income_category.IListIncomesCategoriesCase;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.infra.services.UtilService;

@Component
public class AddIncomeCategoryRequestIncomeCategoryExistsValidation implements IAddIncomeCategoryRequestValidator {

    private IListIncomesCategoriesCase listIncomesCategoriesCase;
    private UtilService utilService;

    public AddIncomeCategoryRequestIncomeCategoryExistsValidation(IListIncomesCategoriesCase listIncomesCategoriesCase,
            UtilService utilService) {
        this.listIncomesCategoriesCase = listIncomesCategoriesCase;
        this.utilService = utilService;
    }

    @Override
    public void validate(AddIncomeCategoryRequest dto, User user) {
        Set<IncomeCategory> categories = listIncomesCategoriesCase.execute(user.getId());
        if (categories.stream().anyMatch(category -> utilService.equalsIgnoreCaseAndAccents(category.getName(), dto.getName())))
            throw new ValueAlreadyInDatabaseException("name", IncomeCategory.class.getSimpleName());
    }

}
