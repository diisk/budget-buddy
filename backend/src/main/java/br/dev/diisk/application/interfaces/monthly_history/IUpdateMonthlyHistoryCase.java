package br.dev.diisk.application.interfaces.monthly_history;

import java.time.LocalDateTime;

import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateMonthlyHistoryCase {

    MonthlyHistory execute(User user, LocalDateTime referenceDate);

}
