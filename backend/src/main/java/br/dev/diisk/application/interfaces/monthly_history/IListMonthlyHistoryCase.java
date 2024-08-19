package br.dev.diisk.application.interfaces.monthly_history;

import java.time.LocalDateTime;
import java.util.List;

import br.dev.diisk.domain.entities.MonthlyHistory;

public interface IListMonthlyHistoryCase {

    List<MonthlyHistory> execute(Long userId, LocalDateTime beginsReference, LocalDateTime endsReference);

}
