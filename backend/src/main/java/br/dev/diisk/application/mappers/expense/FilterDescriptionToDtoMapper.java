package br.dev.diisk.application.mappers.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.FilterDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FilterDescription;

@Component
public class FilterDescriptionToDtoMapper extends BaseMapper<FilterDescription, FilterDescriptionDTO> {

    @Autowired
    private RuleDescriptionToDtoMapper ruleDescriptionToDtoMapper;

    public FilterDescriptionToDtoMapper() {
        super(FilterDescription.class, FilterDescriptionDTO.class);
    }


    @Override
    protected void doComplexMap(FilterDescription source, FilterDescriptionDTO target) {
        target.setRules(ruleDescriptionToDtoMapper.mapList(source.getRules()));
    }

}
