package br.dev.diisk.application.mappers.saving_goal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.saving_goal.SavingGoalDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.infra.services.UtilService;

@Component
public class SavingGoalToDtoMapper extends BaseMapper<SavingGoal, SavingGoalDTO> {

    private final UtilService utilService;

    public SavingGoalToDtoMapper(ModelMapper mapper, UtilService utilService) {
        super(mapper);
        this.utilService = utilService;
    }

    @Override
    protected void doComplexMap(SavingGoal source, SavingGoalDTO target) {
        target.setPercentageAchieved(utilService.divide(source.getCurrentValue(), source.getTargetValue()));
    }

}
