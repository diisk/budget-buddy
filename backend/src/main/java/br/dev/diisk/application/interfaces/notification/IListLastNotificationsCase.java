package br.dev.diisk.application.interfaces.notification;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.notification.Notification;

public interface IListLastNotificationsCase {

    Set<Notification> execute(Long userId, LocalDateTime referenceDate);

}
