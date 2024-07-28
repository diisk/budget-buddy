package br.dev.diisk.application.cases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.CreateIncomeRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.domain.cases.income.ICreateIncomeCase;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IncomeRepository;

@Component
public class AddIncomeCase implements ICreateIncomeCase {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    public Income execute(CreateIncomeRequest dto, User owner) {
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
