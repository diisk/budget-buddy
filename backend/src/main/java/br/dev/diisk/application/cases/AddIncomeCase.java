package br.dev.diisk.application.cases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.domain.cases.income.IAddIncomeCase;
import br.dev.diisk.domain.dtos.income.CreateIncomeDTO;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;

@Component
public class AddIncomeCase implements IAddIncomeCase {

    @Autowired
    private IIncomeRepository incomeRepository;

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Override
    public Income execute(CreateIncomeDTO dto, User owner) {
        Optional<IncomeCategory> foundedCategory = incomeCategoryRepository.findByIdAndUserId(dto.getCategoryId(),
                owner.getId());

        if (foundedCategory.isEmpty())
            throw new DbValueNotFoundException("id", IncomeCategory.class.getName());

        Income income = new Income();
        income.setCategory(foundedCategory.get());
        income.setUser(owner);
        income.setDate(dto.getDate());
        income.setValue(dto.getValue());
        income.setDescription(dto.getDescription());
        return incomeRepository.save(income);
    }

}
