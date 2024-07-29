package br.dev.diisk.application.cases.income;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.UpdateIncomeRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.income.IUpdateIncomeCase;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import jakarta.transaction.Transactional;

@Component
public class UpdateIncomeCase implements IUpdateIncomeCase {

    @Autowired
    private IIncomeRepository incomeRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Override
    @Transactional
    public Income execute(Long id, UpdateIncomeRequest dto, User owner) {
        Optional<Income> foundedIncome = incomeRepository.findById(id);
        if (foundedIncome.isEmpty())
            throw new DbValueNotFoundException("id", Income.class.getSimpleName());

        Income income = foundedIncome.get();

        if (dto.getCategoryId() != null) {
            Optional<IncomeCategory> foundedCategory = incomeCategoryRepository
                    .findByIdAndUserId(dto.getCategoryId(), owner.getId());

            if (foundedCategory.isEmpty())
                throw new DbValueNotFoundException("id", IncomeCategory.class.getSimpleName());

            income.setCategory(foundedCategory.get());
        }

        mapper.map(dto, income);

        return incomeRepository.save(income);

    }

}
