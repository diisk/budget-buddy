package br.dev.diisk.application.mappers.transaction_category;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import br.dev.diisk.application.dtos.transaction_category.AddTransactionCategoryRequest;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

@Component
public class AddTransactionCategoryRequestToTransactionCategoryMapper
        extends BaseMapper<AddTransactionCategoryRequest, TransactionCategory> {

    public AddTransactionCategoryRequestToTransactionCategoryMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void doComplexMap(AddTransactionCategoryRequest source, TransactionCategory target) {
        target.setType(TransactionTypeEnum.getByName(source.getType()).get());
    }
}
