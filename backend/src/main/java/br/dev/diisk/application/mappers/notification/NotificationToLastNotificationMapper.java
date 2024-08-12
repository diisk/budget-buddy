package br.dev.diisk.application.mappers.notification;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.LastBudgetNotificationDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.notification.BudgetNotification;

@Component
public class NotificationToLastNotificationMapper extends BaseMapper<BudgetNotification, LastBudgetNotificationDTO> {

    public NotificationToLastNotificationMapper(ModelMapper mapper) {
        super(mapper);
    }

   @Override
   protected void doComplexMap(BudgetNotification source, LastBudgetNotificationDTO target) {
       target.setCategoryName(source.getCategory().getName());
   }

}
