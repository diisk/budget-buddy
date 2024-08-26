package br.dev.diisk.application.interfaces.notification;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.notification.BudgetNotification;

public interface IListLastBudgetNotificationByCategoryCase {

    Set<BudgetNotification> execute(Long userId, LocalDateTime referenceDate);

}
