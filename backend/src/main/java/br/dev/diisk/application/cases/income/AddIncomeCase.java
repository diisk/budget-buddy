package br.dev.diisk.application.cases.income;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.interfaces.income.IAddIncomeCase;
import br.dev.diisk.application.interfaces.income.IAddIncomeRequestValidator;
import br.dev.diisk.application.mappers.income.AddIncomeRequestMapper;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import jakarta.transaction.Transactional;

@Service
public class AddIncomeCase implements IAddIncomeCase {

    private IIncomeRepository incomeRepository;
    private List<IAddIncomeRequestValidator> validations;
    private AddIncomeRequestMapper addIncomeRequestMapper;

    public AddIncomeCase(IIncomeRepository incomeRepository, List<IAddIncomeRequestValidator> validations,
            AddIncomeRequestMapper addIncomeRequestMapper) {
        this.incomeRepository = incomeRepository;
        this.validations = validations;
        this.addIncomeRequestMapper = addIncomeRequestMapper;
    }

    @Override
    @Transactional
    public Income execute(AddIncomeRequest dto, User owner) {
        List<AddIncomeRequest> toValidate = new ArrayList<>();
        toValidate.add(dto);
        validations.forEach(validation -> validation.validate(toValidate, owner));

        dto.setUserId(owner.getId());
        Income income = addIncomeRequestMapper.apply(dto);
        return incomeRepository.save(income);
    }

}
