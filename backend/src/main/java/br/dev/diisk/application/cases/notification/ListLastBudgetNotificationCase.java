package br.dev.diisk.application.cases.notification;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationCase;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.repositories.notification.IBudgetNotificationRepository;

@Service
public class ListLastBudgetNotificationCase implements IListLastBudgetNotificationCase{

    private IBudgetNotificationRepository notificationRepository;

    public ListLastBudgetNotificationCase(IBudgetNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Set<BudgetNotification> execute(Long userId, LocalDateTime referenceDate) {
        return notificationRepository.findLastNotifications(userId, referenceDate);
    }

}
