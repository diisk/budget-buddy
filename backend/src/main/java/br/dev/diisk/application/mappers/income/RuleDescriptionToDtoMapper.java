package br.dev.diisk.application.mappers.income;


import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.income.RuleDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.RuleDescription;

@Component
public class RuleDescriptionToDtoMapper extends BaseMapper<RuleDescription,RuleDescriptionDTO>{

    public RuleDescriptionToDtoMapper() {
        super(RuleDescription.class,RuleDescriptionDTO.class);
    }

    @Override
    protected void configureMapping(TypeMap<RuleDescription, RuleDescriptionDTO> typeMap) {
        
    }


}
