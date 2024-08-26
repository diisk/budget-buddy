package br.dev.diisk.application.cases.transaction;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.interfaces.fund_storage.IRegisterTransactionValueCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.notification.IListLastBudgetNotificationByCategoryCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.application.mappers.transaction.AddTransactionRequestToTransactionMapper;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.notification.BudgetNotification;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.infra.services.CacheService;
import br.dev.diisk.infra.services.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddTransactionCase implements IAddTransactionCase {

    private final ITransactionRepository transactionRepository;
    private final List<IAddTransactionRequestValidator> validations;
    private final AddTransactionRequestToTransactionMapper addTransactionRequestMapper;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final CacheService cacheService;
    private final IRegisterTransactionValueCase registerTransactionValueCase;
    private final UtilService utilService;
    private final IListLastBudgetNotificationByCategoryCase listLastBudgetNotificationByCategoryCase;

    @Override
    @Transactional
    public Transaction execute(AddTransactionRequest dto, User owner) {
        List<AddTransactionRequest> toValidate = new ArrayList<>();
        toValidate.add(dto);
        validations.forEach(validation -> validation.validate(toValidate, owner));
        dto.setUser(owner);

        cacheService.evictCache("transactions", owner.getId().toString() + "-" + dto.getCategoryId());

        Transaction transaction = addTransactionRequestMapper.apply(dto);
        TransactionCategory category = transaction.getCategory();
        transactionRepository.save(transaction);

        registerTransactionValueCase.execute(
                transaction.getFundStorage().getId(),
                new TransactionValueDTO(
                        transaction.getValue(),
                        transaction.getCategory().getType(),
                        false),
                owner);

        MonthlyHistory monthlyHistory = updateMonthlyHistoryCase.execute(owner, transaction.getDate(),
                category);

        switch (category.getType()) {
            case EXPENSE:
                Set<BudgetNotification> lastNotificationsByCategory = listLastBudgetNotificationByCategoryCase.execute(null, null)
                if (utilService.toReference(LocalDateTime.now()) == monthlyHistory.getReferenceDate()) {
                    BigDecimal percentUsed = utilService.divide(monthlyHistory.getValue(), category.getBudgetLimit());
                    if (percentUsed.compareTo(new BigDecimal(0.8)) > 0) {

                    }
                }
                break;
        }

        return transaction;
    }

}
