package br.dev.diisk.application.cases.transaction;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.transaction.AddTransactionRequest;
import br.dev.diisk.application.interfaces.monthly_history.IUpdateMonthlyHistoryCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionCase;
import br.dev.diisk.application.interfaces.transaction.IAddTransactionRequestValidator;
import br.dev.diisk.application.mappers.transaction.AddTransactionRequestToTransactionMapper;
import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class AddTransactionCase implements IAddTransactionCase {

    private final ITransactionRepository transactionRepository;
    private final List<IAddTransactionRequestValidator> validations;
    private AddTransactionRequestToTransactionMapper addTransactionRequestMapper;
    private final IUpdateMonthlyHistoryCase updateMonthlyHistoryCase;
    private final CacheService cacheService;
    
    public AddTransactionCase(ITransactionRepository transactionRepository, List<IAddTransactionRequestValidator> validations,
            AddTransactionRequestToTransactionMapper addTransactionRequestMapper, IUpdateMonthlyHistoryCase updateMonthlyHistoryCase,
            CacheService cacheService) {
        this.transactionRepository = transactionRepository;
        this.validations = validations;
        this.addTransactionRequestMapper = addTransactionRequestMapper;
        this.updateMonthlyHistoryCase = updateMonthlyHistoryCase;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public Transaction execute(AddTransactionRequest dto, User owner) {
        List<AddTransactionRequest> toValidate = new ArrayList<>();
        toValidate.add(dto);
        validations.forEach(validation -> validation.validate(toValidate, owner));
        dto.setUser(owner);

        cacheService.evictCache("transactions", owner.getId().toString()+"-"+dto.getCategoryId());

        Transaction transaction = addTransactionRequestMapper.apply(dto);
        transactionRepository.save(transaction);

        updateMonthlyHistoryCase.execute(owner, transaction.getDate(), transaction.getCategory());

        return transaction;
    }

}
