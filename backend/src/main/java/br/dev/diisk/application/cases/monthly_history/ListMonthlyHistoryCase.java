package br.dev.diisk.application.cases.monthly_history;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.monthly_history.IListMonthlyHistoryCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.repositories.monthly_history.IMonthlyHistoriRepository;

@Service
public class ListMonthlyHistoryCase implements IListMonthlyHistoryCase {

    private final IMonthlyHistoriRepository monthlyHistoriRepository;

    public ListMonthlyHistoryCase(IMonthlyHistoriRepository monthlyHistoriRepository) {
        this.monthlyHistoriRepository = monthlyHistoriRepository;
    }

    @Override
    @Cacheable(value = "monthly-histories", key = "#userId")
    public List<MonthlyHistory> execute(Long userId) {
        return monthlyHistoriRepository.findAllByUserId(userId).stream()
                .sorted((mh1, mh2) -> mh1.getReferenceDate().compareTo(mh2.getReferenceDate()))
                .collect(Collectors.toList());
    }

}
