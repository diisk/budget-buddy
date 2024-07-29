package br.dev.diisk.application.mappers.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.ExpenseCategoryResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.expense.ExpenseCategory;

@Component
public class ExpenseCategoryToResponseMapper extends BaseMapper<ExpenseCategory, ExpenseCategoryResponse> {

    @Autowired
    private FilterDescriptionToDtoMapper filterDescriptionToDtoMapper;

    public ExpenseCategoryToResponseMapper() {
        super(ExpenseCategory.class, ExpenseCategoryResponse.class);
    }


    @Override
    protected void doComplexMap(ExpenseCategory source, ExpenseCategoryResponse target) {
        target.setFilters(filterDescriptionToDtoMapper.mapList(source.getFilters()));
    }

}
