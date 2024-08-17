package br.dev.diisk.application.interfaces.monthly_history;

import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;

public interface IGetMonthlyHistoryCase {

    MonthlyHistory execute(Long userId, LocalDateTime referenceDate, TransactionCategory category);

}
