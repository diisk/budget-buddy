package br.dev.diisk.application.cases.income;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.income.IListIncomesCase;
import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;

@Service
public class ListIncomesCase implements IListIncomesCase{

    private IIncomeRepository incomeRepository;

    public ListIncomesCase(IIncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    @Cacheable(value = "incomes", key = "#userId+'-'+#beginsAt+'-'+#endsAt")
    public Set<Income> execute(Long userId, LocalDateTime beginsAt, LocalDateTime endsAt) {
        if(endsAt==null) endsAt = LocalDateTime.now();
        return incomeRepository.findAllByUserIdAndPeriod(userId, beginsAt, endsAt);
    }

}
