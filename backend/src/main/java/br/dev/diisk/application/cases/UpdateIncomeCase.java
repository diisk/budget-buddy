package br.dev.diisk.application.cases;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.domain.cases.income.IUpdateIncomeCase;
import br.dev.diisk.domain.dtos.income.UpdateIncomeDTO;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;

@Component
public class UpdateIncomeCase implements IUpdateIncomeCase {

    @Autowired
    private IIncomeRepository incomeRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IIncomeCategoryRepository incomeCategoryRepository;

    @Override
    public Income execute(Long id, UpdateIncomeDTO dto, User owner) {
        Optional<Income> foundedIncome = incomeRepository.findById(id);
        if (foundedIncome.isEmpty())
            throw new DbValueNotFoundException("id", Income.class.getName());

        Income income = foundedIncome.get();

        if (dto.getCategoryId() != null) {
            Optional<IncomeCategory> foundedCategory = incomeCategoryRepository
                    .findByIdAndUserId(dto.getCategoryId(), owner.getId());

            if (foundedCategory.isEmpty())
                throw new DbValueNotFoundException("id", IncomeCategory.class.getName());

            income.setCategory(foundedCategory.get());
        }

        mapper.map(dto, income);

        return incomeRepository.save(income);

    }

}
