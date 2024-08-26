package br.dev.diisk.application.interfaces.notification;

import br.dev.diisk.domain.entities.MonthlyHistory;

public interface IUpdateBudgetNotificationCase {

    void execute(MonthlyHistory monthlyHistory);

}
