package br.dev.diisk.application.interfaces.notification;

import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.entities.user.User;

public interface IAddNotificationCase {

    Notification execute(String message, User user);

}
