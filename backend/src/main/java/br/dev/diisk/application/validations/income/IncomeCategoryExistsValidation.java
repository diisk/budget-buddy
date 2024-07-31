package br.dev.diisk.application.validations.income;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.income.ICreateIncomeCategoryValidator;
import br.dev.diisk.application.dtos.income.CreateIncomeCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;

@Component
public class IncomeCategoryExistsValidation implements ICreateIncomeCategoryValidator {

    private IIncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryExistsValidation(IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    public void validate(CreateIncomeCategoryRequest dto, User user) {
        if (incomeCategoryRepository.existsByNameAndUserId(dto.getName(), user.getId()))
            throw new ValueAlreadyInDatabaseException("name", IncomeCategory.class.getSimpleName());
    }

}
