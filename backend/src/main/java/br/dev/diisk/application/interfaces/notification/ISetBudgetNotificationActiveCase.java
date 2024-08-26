package br.dev.diisk.application.interfaces.notification;

import br.dev.diisk.domain.entities.user.User;

public interface ISetBudgetNotificationActiveCase {

    void execute(Long id, Boolean active, User user);

}
