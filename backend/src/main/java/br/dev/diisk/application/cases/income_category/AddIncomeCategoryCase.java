package br.dev.diisk.application.cases.income_category;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.income_category.AddIncomeCategoryRequest;
import br.dev.diisk.application.interfaces.income_category.IAddIncomeCategoryCase;
import br.dev.diisk.application.interfaces.income_category.IAddIncomeCategoryRequestValidator;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddIncomeCategoryCase implements IAddIncomeCategoryCase {

    private List<IAddIncomeCategoryRequestValidator> validators;
    private IIncomeCategoryRepository incomeCategoryRepository;

    public AddIncomeCategoryCase(List<IAddIncomeCategoryRequestValidator> validators,
            IIncomeCategoryRepository incomeCategoryRepository) {
        this.validators = validators;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    @Transactional
    @CacheEvict(value = "incomes-categories", allEntries = true)
    public IncomeCategory execute(AddIncomeCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setName(dto.getName());
        incomeCategory.setUser(owner);
        return incomeCategoryRepository.save(incomeCategory);
    }

}
