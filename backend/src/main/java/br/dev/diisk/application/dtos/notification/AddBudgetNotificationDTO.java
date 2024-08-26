package br.dev.diisk.application.dtos.notification;

import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AddBudgetNotificationDTO {
    private TransactionCategory category;
    private String message;
}
