package br.dev.diisk.application.mappers.saving_goal;

import java.math.RoundingMode;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.saving_goal.SavingGoalDTO;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.SavingGoal;

@Component
public class SavingGoalToDtoMapper extends BaseMapper<SavingGoal, SavingGoalDTO> {

    public SavingGoalToDtoMapper(ModelMapper mapper) {
        super(mapper);
    }

   @Override
   protected void doComplexMap(SavingGoal source, SavingGoalDTO target) {
       target.setPercentageAchieved(source.getCurrentValue().divide(source.getTargetValue(),2,RoundingMode.HALF_EVEN));
   }

}
