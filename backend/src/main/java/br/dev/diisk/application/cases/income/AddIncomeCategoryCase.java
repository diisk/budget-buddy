package br.dev.diisk.application.cases.income;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.interfaces.income.CreateIncomeCategoryValidator;
import br.dev.diisk.application.interfaces.income.ICreateCategoryCase;
import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import jakarta.transaction.Transactional;

@Component
public class AddIncomeCategoryCase implements ICreateCategoryCase {

    @Autowired
    private List<CreateIncomeCategoryValidator> validators;

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Override
    @Transactional
    public IncomeCategory execute(CreateCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setName(dto.getName());
        incomeCategory.setUser(owner);
        return incomeCategoryRepository.save(incomeCategory);
    }

}
