package br.dev.diisk.application.cases.expense;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.expense.AddExpenseRequest;
import br.dev.diisk.application.interfaces.expense.IAddExpenseCase;
import br.dev.diisk.application.interfaces.expense.IAddExpenseRequestValidator;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.mappers.expense.AddExpenseRequestMapper;
import br.dev.diisk.domain.entities.expense.Expense;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.expense.IExpenseRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class AddExpenseCase implements IAddExpenseCase {

    private IExpenseRepository expenseRepository;
    private List<IAddExpenseRequestValidator> validations;
    private AddExpenseRequestMapper addExpenseRequestMapper;
    private IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private CacheService cacheService;
    
    public AddExpenseCase(IExpenseRepository expenseRepository, List<IAddExpenseRequestValidator> validations,
            AddExpenseRequestMapper addExpenseRequestMapper, IUpdateMonthlyHistoryCase updateMonthlyHistoryCase,
            CacheService cacheService) {
        this.expenseRepository = expenseRepository;
        this.validations = validations;
        this.addExpenseRequestMapper = addExpenseRequestMapper;
        this.updateMonthlyHistoryCase = updateMonthlyHistoryCase;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public Expense execute(AddExpenseRequest dto, User owner) {
        List<AddExpenseRequest> toValidate = new ArrayList<>();
        toValidate.add(dto);
        validations.forEach(validation -> validation.validate(toValidate, owner));
        dto.setUser(owner);

        cacheService.evictCache("expenses", owner.getId().toString());

        Expense expense = addExpenseRequestMapper.apply(dto);
        expenseRepository.save(expense);

        updateMonthlyHistoryCase.execute(owner, expense.getDate());

        return expense;
    }

}
