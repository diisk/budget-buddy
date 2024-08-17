package br.dev.diisk.application.cases.transaction_category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.IAddTransactionCategoryRequestValidator;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class AddTransactionCategoryCase implements IAddTransactionCategoryCase {

    private final List<IAddTransactionCategoryRequestValidator> validators;
    private final ITransactionCategoryRepository transactionCategoryRepository;
    private final ModelMapper mapper;

    public AddTransactionCategoryCase(List<IAddTransactionCategoryRequestValidator> validators,
            ITransactionCategoryRepository transactionCategoryRepository, ModelMapper mapper) {
        this.validators = validators;
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = "transactions-categories", key = "#owner.getId()+'-'+#dto.getType()")
    public TransactionCategory execute(AddTransactionCategoryRequest dto, User owner) {
        validators.forEach(validation -> validation.validate(dto, owner));
        dto.setUser(owner);
        TransactionCategory transactionCategory = mapper.map(dto, TransactionCategory.class);
        return transactionCategoryRepository.save(transactionCategory);
    }

}
