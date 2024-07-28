package br.dev.diisk.application.cases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.interfaces.income.CreateIncomeCategoryValidator;
import br.dev.diisk.domain.cases.income.ICreateCategoryCase;
import br.dev.diisk.application.dtos.income.CreateCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IncomeCategoryRepository;

@Component
public class AddIncomeCategoryCase implements ICreateCategoryCase {

    @Autowired
    private List<CreateIncomeCategoryValidator> validators;

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    public IncomeCategory execute(CreateCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        IncomeCategory incomeCategory = new IncomeCategory(dto);
        incomeCategory.setUser(owner);
        return incomeCategoryRepository.save(incomeCategory);
    }

}
