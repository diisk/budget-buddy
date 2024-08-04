package br.dev.diisk.application.cases.income;

import java.time.LocalDateTime;
import java.util.Set;

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
    public Set<Income> execute(Long userId, LocalDateTime beginsAt, LocalDateTime endsAt) {
        return incomeRepository.findAllByUserIdAndPeriod(userId, beginsAt, endsAt);
    }

}
