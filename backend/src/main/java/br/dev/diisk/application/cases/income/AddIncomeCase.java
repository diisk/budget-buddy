package br.dev.diisk.application.cases.income;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.income.AddIncomeRequest;
import br.dev.diisk.application.interfaces.income.IAddIncomeCase;
import br.dev.diisk.application.interfaces.income.IAddIncomeRequestValidator;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.mappers.income.AddIncomeRequestMapper;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class AddIncomeCase implements IAddIncomeCase {

    private IIncomeRepository incomeRepository;
    private List<IAddIncomeRequestValidator> validations;
    private AddIncomeRequestMapper addIncomeRequestMapper;
    private IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private CacheService cacheService;

    public AddIncomeCase(IIncomeRepository incomeRepository, List<IAddIncomeRequestValidator> validations,
            AddIncomeRequestMapper addIncomeRequestMapper, IUpdateMonthlyHistoryCase updateMonthlyHistoryCase,
            CacheService cacheService) {
        this.incomeRepository = incomeRepository;
        this.validations = validations;
        this.addIncomeRequestMapper = addIncomeRequestMapper;
        this.updateMonthlyHistoryCase = updateMonthlyHistoryCase;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public Income execute(AddIncomeRequest dto, User owner) {
        List<AddIncomeRequest> toValidate = new ArrayList<>();
        toValidate.add(dto);
        validations.forEach(validation -> validation.validate(toValidate, owner));

        dto.setUser(owner);

        cacheService.evictCache("incomes", owner.getId().toString());

        Income income = addIncomeRequestMapper.apply(dto);
        incomeRepository.save(income);

        updateMonthlyHistoryCase.execute(owner, income.getDate());
        
        return income;
    }

}
