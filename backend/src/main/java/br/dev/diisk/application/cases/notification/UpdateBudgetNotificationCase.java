package br.dev.diisk.application.cases.notification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.notification.AddBudgetNotificationDTO;
import br.dev.diisk.application.exceptions.domain.InvalidTransactionTypeException;
import br.dev.diisk.application.interfaces.notification.IAddBudgetNotificationCase;
import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationByCategoryCase;
import br.dev.diisk.application.interfaces.notification.IUpdateBudgetNotificationCase;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.enums.TransactionTypeEnum;
import br.dev.diisk.domain.repositories.notification.IBudgetNotificationRepository;
import br.dev.diisk.infra.services.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateBudgetNotificationCase implements IUpdateBudgetNotificationCase {

    private final IBudgetNotificationRepository budgetNotificationRepository;
    private final IListLastBudgetNotificationByCategoryCase listLastBudgetNotificationByCategoryCase;
    private final IAddBudgetNotificationCase addBudgetNotificationCase;
    private final UtilService utilService;

    @Override
    @Transactional
    public void execute(MonthlyHistory monthlyHistory) {
        TransactionCategory category = monthlyHistory.getCategory();

        if (category.getType() != TransactionTypeEnum.EXPENSE)
            throw new InvalidTransactionTypeException(getClass(), category.getType().toString());

        LocalDateTime now = LocalDateTime.now();
        User user = monthlyHistory.getUser();

        if (utilService.toReference(now) == monthlyHistory.getReferenceDate()) {
            BigDecimal percentToNotify = new BigDecimal(0.8);
            BigDecimal percentUsed = utilService.divide(monthlyHistory.getValue(), category.getBudgetLimit());
            Boolean sendNotification = percentUsed.compareTo(percentToNotify) > 0;
            Optional<BudgetNotification> lastNotification = listLastBudgetNotificationByCategoryCase
                    .execute(user.getId(), now).stream()
                    .filter(bn -> bn.getCategory().getId() == category.getId()
                            && bn.getCreatedAt().getMonthValue() == now.getMonthValue()
                            && bn.getCreatedAt().getYear() == now.getYear())
                    .findFirst();

            if (lastNotification.isPresent() && !sendNotification) {
                lastNotification.get().remove();
                budgetNotificationRepository.save(lastNotification.get());
            }

            if (lastNotification.isEmpty() && sendNotification) {
                Integer percent = percentToNotify.multiply(new BigDecimal(10)).intValue();
                AddBudgetNotificationDTO dto = new AddBudgetNotificationDTO();
                dto.setCategory(category);
                dto.setMessage("Você atingiu %d%% do seu orçamento.".formatted(percent));
                BudgetNotification budgetNotification = addBudgetNotificationCase.execute(dto, user);
                budgetNotificationRepository.save(budgetNotification);
            }
        }
    }

}
