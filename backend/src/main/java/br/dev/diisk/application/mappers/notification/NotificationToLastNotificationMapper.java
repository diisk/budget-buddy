package br.dev.diisk.application.mappers.notification;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.LastNotificationDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.notification.Notification;

@Component
public class NotificationToLastNotificationMapper extends BaseMapper<Notification, LastNotificationDTO> {

    public NotificationToLastNotificationMapper(ModelMapper mapper) {
        super(mapper);
    }

   @Override
   protected void doComplexMap(Notification source, LastNotificationDTO target) {
       target.setCategoryName(source.getCategory().getName());
   }

}
