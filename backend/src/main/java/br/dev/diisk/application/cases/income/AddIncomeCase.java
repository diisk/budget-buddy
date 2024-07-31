package br.dev.diisk.application.cases.income;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.income.CreateIncomeRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.income.ICreateIncomeCase;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import jakarta.transaction.Transactional;

@Service
public class AddIncomeCase implements ICreateIncomeCase {

    private IIncomeRepository incomeRepository;
    private IIncomeCategoryRepository incomeCategoryRepository;

    public AddIncomeCase(IIncomeRepository incomeRepository, IIncomeCategoryRepository incomeCategoryRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    @Override
    @Transactional
    public Income execute(CreateIncomeRequest dto, User owner) {
        Optional<IncomeCategory> foundedCategory = incomeCategoryRepository.findByIdAndUserId(dto.getCategoryId(),
                owner.getId());

        if (foundedCategory.isEmpty())
            throw new DbValueNotFoundException("id", IncomeCategory.class.getSimpleName());

        Income income = new Income();
        income.setCategory(foundedCategory.get());
        income.setUser(owner);
        income.setDate(dto.getDate());
        income.setValue(dto.getValue());
        income.setDescription(dto.getDescription());
        return incomeRepository.save(income);
    }

}
