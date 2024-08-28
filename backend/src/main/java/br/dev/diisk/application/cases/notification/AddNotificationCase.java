package br.dev.diisk.application.cases.notification;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.notification.IAddNotificationCase;
import br.dev.diisk.domain.entities.notification.Notification;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.notification.INotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddNotificationCase implements IAddNotificationCase {

    private final INotificationRepository notificationRepository;

    @Override
    @Transactional
    public Notification execute(String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        return notificationRepository.save(notification);
    }

}
