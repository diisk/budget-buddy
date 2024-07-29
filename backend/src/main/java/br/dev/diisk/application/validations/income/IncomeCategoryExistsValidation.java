package br.dev.diisk.application.validations.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.income.CreateIncomeCategoryValidator;
import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;

@Component
public class IncomeCategoryExistsValidation implements CreateIncomeCategoryValidator {

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Override
    public void validate(CreateCategoryRequest dto, User user) {
        if (incomeCategoryRepository.existsByNameAndUserId(dto.getName(), user.getId()))
            throw new ValueAlreadyInDatabaseException("name", IncomeCategory.class.getSimpleName());
    }

}
