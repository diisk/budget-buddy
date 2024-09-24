package br.dev.diisk.application.cases.monthly_history;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.UtilService;
import br.dev.diisk.application.exceptions.date.FutureDateException;
import br.dev.diisk.application.interfaces.monthly_history.IGetMonthlyHistoryCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.repositories.monthly_history.IMonthlyHistoriRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetMonthlyHistoryCase implements IGetMonthlyHistoryCase {

    private final UtilService utilService;
    private final IMonthlyHistoriRepository monthlyHistoriRepository;

    @Override
    public MonthlyHistory execute(Long userId, LocalDateTime referenceDate, TransactionCategory category) {
        referenceDate = utilService.toReference(referenceDate);

        if (referenceDate.isAfter(LocalDateTime.now()))
            throw new FutureDateException(getClass(), "referenceDate");

        return monthlyHistoriRepository
                .findByReferenceDateAndUserId(referenceDate, userId).orElse(null);
    }

}
