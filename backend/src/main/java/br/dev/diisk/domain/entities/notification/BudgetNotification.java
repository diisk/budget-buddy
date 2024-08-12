package br.dev.diisk.domain.entities.notification;

import br.dev.diisk.domain.entities.expense.ExpenseCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "budget_notifications")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "notificationId")
public class BudgetNotification extends Notification{

    @ManyToOne(optional = false)
    private ExpenseCategory category;

}
