package br.dev.diisk.application.mappers.expense;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.FilterDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FilterDescription;

@Component
public class FilterDescriptionToDtoMapper extends BaseMapper<FilterDescription, FilterDescriptionDTO> {

    private RuleDescriptionToDtoMapper ruleDescriptionToDtoMapper;

    public FilterDescriptionToDtoMapper(ModelMapper mapper, RuleDescriptionToDtoMapper ruleDescriptionToDtoMapper) {
        super(mapper);
        this.ruleDescriptionToDtoMapper = ruleDescriptionToDtoMapper;
    }

    @Override
    protected void doComplexMap(FilterDescription source, FilterDescriptionDTO target) {
        target.setRules(ruleDescriptionToDtoMapper.mapList(source.getRules()));
    }

}
