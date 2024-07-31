package br.dev.diisk.application.mappers.expense;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.expense.RuleDescriptionDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.RuleDescription;

@Component
public class RuleDescriptionToDtoMapper extends BaseMapper<RuleDescription,RuleDescriptionDTO>{

    public RuleDescriptionToDtoMapper(ModelMapper mapper) {
        super(mapper);
    }

}
