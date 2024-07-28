package br.dev.diisk.application.mappers.income;

import java.util.Collection;
import java.util.Set;

import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.FilterDescriptionDTO;
import br.dev.diisk.application.dtos.income.RuleDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.FilterDescription;
import br.dev.diisk.domain.entities.RuleDescription;

@Component
public class FilterDescriptionToDtoMapper extends BaseMapper<FilterDescription, FilterDescriptionDTO> {

    @Autowired
    private RuleDescriptionToDtoMapper ruleDescriptionToDtoMapper;

    public FilterDescriptionToDtoMapper() {
        super(FilterDescription.class, FilterDescriptionDTO.class);
    }

    @Override
    protected void configureMapping(TypeMap<FilterDescription, FilterDescriptionDTO> typeMap) {
        Converter<Collection<RuleDescription>, Set<RuleDescriptionDTO>> converter = c -> ruleDescriptionToDtoMapper
                .mapSet(c.getSource());

        typeMap
                .addMappings(
                        mapper -> mapper.using(converter).map(FilterDescription::getRules,
                                FilterDescriptionDTO::setRules));
    }

}
