package br.dev.diisk.application.mappers.income;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.IncomeCategoryResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.income.IncomeCategory;

@Component
public class IncomeCategoryToResponseMapper extends BaseMapper<IncomeCategory, IncomeCategoryResponse> {

    public IncomeCategoryToResponseMapper(ModelMapper mapper) {
        super(mapper);
    }

}
