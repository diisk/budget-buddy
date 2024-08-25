package br.dev.diisk.application.cases.notification;

import java.util.List;

import org.springframework.stereotype.Service;
import br.dev.diisk.application.interfaces.notification.IListNotificationCase;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.repositories.notification.INotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListNotificationCase implements IListNotificationCase{

    private final INotificationRepository notificationRepository;

    @Override
    public List<Notification> execute(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

}
