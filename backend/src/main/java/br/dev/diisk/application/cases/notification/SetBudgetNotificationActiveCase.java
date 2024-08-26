package br.dev.diisk.application.cases.notification;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.cases.rastreable_entity.SetEntityActiveCase;
import br.dev.diisk.application.interfaces.notification.ISetBudgetNotificationActiveCase;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.repositories.notification.IBudgetNotificationRepository;

@Service
public class SetBudgetNotificationActiveCase extends SetEntityActiveCase<BudgetNotification>
        implements ISetBudgetNotificationActiveCase {

    public SetBudgetNotificationActiveCase(IBudgetNotificationRepository repository) {
        super(repository);
    }

}
