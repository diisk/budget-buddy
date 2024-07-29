package br.dev.diisk.application.mappers.expense;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.ExpenseResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.expense.Expense;

@Component
public class ExpenseToResponseMapper extends BaseMapper<Expense, ExpenseResponse> {

    public ExpenseToResponseMapper() {
        super(Expense.class, ExpenseResponse.class);
    }


    @Override
    protected void doComplexMap(Expense source, ExpenseResponse target) {
        target.setCategoryName(source.getCategory().getName());
    }

}
