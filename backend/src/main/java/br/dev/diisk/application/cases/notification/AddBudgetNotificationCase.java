package br.dev.diisk.application.cases.notification;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.notification.AddBudgetNotificationDTO;
import br.dev.diisk.application.interfaces.notification.IAddBudgetNotificationCase;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.notification.IBudgetNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddBudgetNotificationCase implements IAddBudgetNotificationCase {

    private final IBudgetNotificationRepository bugetNotificationRepository;

    @Override
    @Transactional
    public BudgetNotification execute(AddBudgetNotificationDTO dto, User user) {
        BudgetNotification notification = new BudgetNotification();
        notification.setUser(user);
        notification.setCategory(dto.getCategory());
        notification.setMessage(dto.getMessage());
        return bugetNotificationRepository.save(notification);
    }

}
