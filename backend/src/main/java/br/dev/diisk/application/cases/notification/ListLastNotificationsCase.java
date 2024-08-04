package br.dev.diisk.application.cases.notification;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.notification.IListLastNotificationsCase;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.repositories.notification.INotificationRepository;

@Service
public class ListLastNotificationsCase implements IListLastNotificationsCase{

    private INotificationRepository notificationRepository;

    public ListLastNotificationsCase(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Set<Notification> execute(Long userId, LocalDateTime referenceDate) {
        return notificationRepository.findLastNotifications(userId, referenceDate);
    }

}
