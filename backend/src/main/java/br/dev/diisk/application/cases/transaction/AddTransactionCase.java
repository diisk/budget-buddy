package br.dev.diisk.application.cases.transaction;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.interfaces.fund_storage.IRegisterTransactionValueCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.notification.IUpdateBudgetNotificationCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.application.mappers.transaction.AddTransactionRequestToTransactionMapper;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.application.interfaces.cache.ICacheService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddTransactionCase implements IAddTransactionCase {

    private final ITransactionRepository transactionRepository;
    private final List<IAddTransactionRequestValidator> validations;
    private final AddTransactionRequestToTransactionMapper addTransactionRequestMapper;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final ICacheService cacheService;
    private final IRegisterTransactionValueCase registerTransactionValueCase;
    private final IUpdateBudgetNotificationCase updateBudgetNotificationCase;

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

        updateBudgetNotificationCase.execute(monthlyHistory);

        return transaction;
    }

}
