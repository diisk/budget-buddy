package br.dev.diisk.application.cases.notification;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationByCategoryCase;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.repositories.notification.IBudgetNotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListLastBudgetNotificationByCategoryCase implements IListLastBudgetNotificationByCategoryCase{

    private final IBudgetNotificationRepository notificationRepository;
    
    @Override
    public Set<BudgetNotification> execute(Long userId, LocalDateTime referenceDate) {
        return notificationRepository.findLastNotifications(userId, referenceDate);
    }

}
