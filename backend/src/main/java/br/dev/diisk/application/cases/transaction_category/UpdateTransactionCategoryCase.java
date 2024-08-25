package br.dev.diisk.application.cases.transaction_category;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import br.dev.diisk.application.dtos.transaction_category.UpdateTransactionCategoryRequest;
import br.dev.diisk.application.interfaces.transaction_category.IGetTransactionCategoryCase;
import br.dev.diisk.application.interfaces.transaction_category.IUpdateTransactionCategoryCase;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.transaction.ITransactionCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateTransactionCategoryCase implements IUpdateTransactionCategoryCase {

    private final ITransactionCategoryRepository transactionCategoryRepository;
    private final IGetTransactionCategoryCase getTransactionCategoryCase;
    private final ModelMapper mapper;

    @Override
    @Transactional
    @CacheEvict(value = "transactions-categories", key = "#owner.getId()+'-'+#dto.getType()")
    public TransactionCategory execute(Long id, UpdateTransactionCategoryRequest dto, User owner) {
        TransactionCategory category = getTransactionCategoryCase.execute(id, owner);
        mapper.map(dto, category);
        return transactionCategoryRepository.save(category);
    }

}
