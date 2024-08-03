package br.dev.diisk.application.cases.income;

import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.income.IAddIncomeCategoryValidator;
import br.dev.diisk.application.interfaces.income.IAddIncomeCategoryCase;
import br.dev.diisk.application.dtos.income.AddIncomeCategoryRequest;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddIncomeCategoryCase implements IAddIncomeCategoryCase {

    private List<IAddIncomeCategoryValidator> validators;
    private IIncomeCategoryRepository incomeCategoryRepository;

    public AddIncomeCategoryCase(List<IAddIncomeCategoryValidator> validators,
            IIncomeCategoryRepository incomeCategoryRepository) {
        this.validators = validators;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    @Transactional
    public IncomeCategory execute(AddIncomeCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setName(dto.getName());
        incomeCategory.setUser(owner);
        return incomeCategoryRepository.save(incomeCategory);
    }

}
