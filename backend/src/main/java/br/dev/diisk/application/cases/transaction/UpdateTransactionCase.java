package br.dev.diisk.application.cases.transaction;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.fund_storage.UpdateFundStorageDTO;
import br.dev.diisk.application.dtos.transaction.UpdateTransactionRequest;
import br.dev.diisk.application.interfaces.fund_storage.IUpdateFundStorageCase;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.transaction.IGetTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IUpdateTransactionRequestValidator;
import br.dev.diisk.application.mappers.transaction.UpdateTransactionRequestToTransactionMapper;
import br.dev.diisk.domain.entities.FundStorage;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class UpdateTransactionCase implements IUpdateTransactionCase {

    private final ITransactionRepository transactionRepository;
    private final List<IUpdateTransactionRequestValidator> validations;
    private final IGetTransactionCase getTransactionCase;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final CacheService cacheService;
    private final IUpdateFundStorageCase updateFundStorageCase;
    private final UpdateTransactionRequestToTransactionMapper updateTransactionRequestToTransactionMapper;
    private final ModelMapper mapper;

    public UpdateTransactionCase(ITransactionRepository transactionRepository,
            List<IUpdateTransactionRequestValidator> validations, IGetTransactionCase getTransactionCase,
            IUpdateMonthlyHistoryCase updateMonthlyHistoryCase, CacheService cacheService,
            IUpdateFundStorageCase updateFundStorageCase,
            UpdateTransactionRequestToTransactionMapper updateTransactionRequestToTransactionMapper,
            ModelMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.validations = validations;
        this.getTransactionCase = getTransactionCase;
        this.updateMonthlyHistoryCase = updateMonthlyHistoryCase;
        this.cacheService = cacheService;
        this.updateFundStorageCase = updateFundStorageCase;
        this.updateTransactionRequestToTransactionMapper = updateTransactionRequestToTransactionMapper;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Transaction execute(Long id, UpdateTransactionRequest dto, User owner) {
        validations.forEach(validation -> validation.validate(id, dto, owner));
        dto.setUser(owner);

        cacheService.evictCache("transactions", owner.getId().toString() + "-" + dto.getCategoryId());

        Transaction transaction = getTransactionCase.execute(id, owner);

        updateFundStorage(transaction, true);
        updateTransactionRequestToTransactionMapper.update(dto, transaction);
        updateFundStorage(transaction, false);
        transactionRepository.save(transaction);

        UpdateFundStorageDTO newBalance = mapper.map(transaction, UpdateFundStorageDTO.class);

        updateFundStorageCase.execute(transaction.getFundStorage().getId(), newBalance, owner);

        updateMonthlyHistoryCase.execute(owner, transaction.getDate(), transaction.getCategory());

        return transaction;
    }

    private void updateFundStorage(Transaction transaction, Boolean undo) {
        FundStorage fundStorage = transaction.getFundStorage();
        Boolean subtract;
        switch (transaction.getCategory().getType()) {
            case EXPENSE:
                subtract = !undo;
                break;
            case INCOME:
                subtract = undo;
                break;
            default:
                throw new NotImplementedException("Not implemented.");
        }

        if (subtract) {
            fundStorage.setBalance(fundStorage.getBalance().subtract(transaction.getValue()));
        } else {
            fundStorage.setBalance(fundStorage.getBalance().add(transaction.getValue()));
        }
    }

}
