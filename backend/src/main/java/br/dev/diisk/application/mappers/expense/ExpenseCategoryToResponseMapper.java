package br.dev.diisk.application.mappers.expense;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.ExpenseCategoryResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;

@Component
public class ExpenseCategoryToResponseMapper extends BaseMapper<ExpenseCategory, ExpenseCategoryResponse> {

    private FilterDescriptionToDtoMapper filterDescriptionToDtoMapper;

    public ExpenseCategoryToResponseMapper(ModelMapper mapper,
            FilterDescriptionToDtoMapper filterDescriptionToDtoMapper) {
        super(mapper);
        this.filterDescriptionToDtoMapper = filterDescriptionToDtoMapper;
    }

    @Override
    protected void doComplexMap(ExpenseCategory source, ExpenseCategoryResponse target) {
        target.setFilters(filterDescriptionToDtoMapper.mapList(source.getFilters()));
    }

}
