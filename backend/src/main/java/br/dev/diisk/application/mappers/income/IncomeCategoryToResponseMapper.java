package br.dev.diisk.application.mappers.income;

import java.util.Collection;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.FilterDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FilterDescription;
import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.infra.dtos.income.IncomeCategoryResponse;

@Component
public class IncomeCategoryToResponseMapper extends BaseMapper<IncomeCategory, IncomeCategoryResponse> {

    @Autowired
    private FilterDescriptionToDtoMapper filterDescriptionToDtoMapper;

    public IncomeCategoryToResponseMapper() {
        super(IncomeCategory.class, IncomeCategoryResponse.class);
    }

    @Override
    protected void configureMapping(TypeMap<IncomeCategory, IncomeCategoryResponse> typeMap) {
        Converter<Collection<FilterDescription>, List<FilterDescriptionDTO>> converter = c -> filterDescriptionToDtoMapper
                .mapList(c.getSource());

        typeMap
                .addMappings(
                        mapper -> mapper.using(converter).map(IncomeCategory::getFilters,
                                IncomeCategoryResponse::setFilters));
    }

}
