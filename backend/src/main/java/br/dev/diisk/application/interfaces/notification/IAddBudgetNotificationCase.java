package br.dev.diisk.application.interfaces.notification;

import br.dev.diisk.application.dtos.notification.AddBudgetNotificationDTO;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.user.User;

public interface IAddBudgetNotificationCase {

    BudgetNotification execute(AddBudgetNotificationDTO dto, User user);

}
