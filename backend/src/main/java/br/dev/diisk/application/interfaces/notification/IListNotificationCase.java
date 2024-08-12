package br.dev.diisk.application.interfaces.notification;

import java.util.List;
import br.dev.diisk.domain.entities.notification.Notification;

public interface IListNotificationCase {

    List<Notification> execute(Long userId);

}
