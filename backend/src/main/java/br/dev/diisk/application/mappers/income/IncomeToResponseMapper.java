package br.dev.diisk.application.mappers.income;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.IncomeResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.income.Income;

@Component
public class IncomeToResponseMapper extends BaseMapper<Income, IncomeResponse> {

    public IncomeToResponseMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void doComplexMap(Income source, IncomeResponse target) {
        target.setCategoryName(source.getCategory().getName());
    }

}
