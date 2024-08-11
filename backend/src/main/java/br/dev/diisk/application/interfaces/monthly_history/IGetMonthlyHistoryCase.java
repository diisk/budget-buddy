package br.dev.diisk.application.interfaces.monthly_history;

import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.MonthlyHistory;

public interface IGetMonthlyHistoryCase {

    MonthlyHistory execute(Long userId, LocalDateTime referenceDate);

}
