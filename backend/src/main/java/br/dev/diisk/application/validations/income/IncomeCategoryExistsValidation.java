package br.dev.diisk.application.validations.income;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.income.IAddIncomeCategoryValidator;
import br.dev.diisk.application.dtos.income.AddIncomeCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;

@Component
public class IncomeCategoryExistsValidation implements IAddIncomeCategoryValidator {

    private IIncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryExistsValidation(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    public void validate(AddIncomeCategoryRequest dto, User user) {
        if (incomeCategoryRepository.existsByNameAndUserId(dto.getName(), user.getId()))
            throw new ValueAlreadyInDatabaseException("name", IncomeCategory.class.getSimpleName());
    }

}
