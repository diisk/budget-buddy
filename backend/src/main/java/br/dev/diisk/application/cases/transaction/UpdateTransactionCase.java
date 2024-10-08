package br.dev.diisk.application.cases.transaction;

import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.TransactionValueDTO;
import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.interfaces.fund_storage.IRegisterTransactionValueCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.notification.IUpdateBudgetNotificationCase;
import br.dev.diisk.application.interfaces.transaction.IGetTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionRequestValidator;
import br.dev.diisk.application.mappers.transaction.UpdateTransactionRequestToTransactionMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.MonthlyHistory;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.application.interfaces.cache.ICacheService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateTransactionCase implements IUpdateTransactionCase {

    private final ITransactionRepository transactionRepository;
    private final List<IUpdateTransactionRequestValidator> validations;
    private final IGetTransactionCase getTransactionCase;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final ICacheService cacheService;
    private final IRegisterTransactionValueCase registerTransactionValueCase;
    private final UpdateTransactionRequestToTransactionMapper updateTransactionRequestToTransactionMapper;
    private final IUpdateBudgetNotificationCase updateBudgetNotificationCase;

    @Override
    @Transactional
    public Transaction execute(Long id, UpdateTransactionRequest dto, User owner) {
        validations.forEach(validation -> validation.validate(id, dto, owner));
        dto.setUser(owner);

        cacheService.evictCache("transactions", owner.getId().toString() + "-" + dto.getCategoryId());

        Transaction transaction = getTransactionCase.execute(id, owner);
        FundStorage fundStorage = transaction.getFundStorage();

        registerTransactionValueCase.execute(fundStorage.getId(), new TransactionValueDTO(
                transaction.getValue(),
                transaction.getCategory().getType(),
                true), owner);

        updateTransactionRequestToTransactionMapper.update(dto, transaction);

        fundStorage = registerTransactionValueCase.execute(fundStorage.getId(), new TransactionValueDTO(
                transaction.getValue(),
                transaction.getCategory().getType(),
                false), owner);

        transaction.setFundStorage(fundStorage);

        transactionRepository.save(transaction);

        MonthlyHistory monthlyHistory = updateMonthlyHistoryCase.execute(owner, transaction.getDate(),
                transaction.getCategory());

        updateBudgetNotificationCase.execute(monthlyHistory);

        return transaction;
    }

}
