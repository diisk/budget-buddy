package br.dev.diisk.application.mappers.expense_category;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense_category.ExpenseCategoryResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;

@Component
public class ExpenseCategoryToResponseMapper extends BaseMapper<ExpenseCategory, ExpenseCategoryResponse> {

    public ExpenseCategoryToResponseMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void doComplexMap(ExpenseCategory source, ExpenseCategoryResponse target) {
        // target.setFilters(filterDescriptionToDtoMapper.mapList(source.getFilters()));
    }

}
