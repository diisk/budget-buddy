package br.dev.diisk.application.mappers.saving_goal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalResponse;
import br.dev.diisk.application.mappers.BaseMapper;
import br.dev.diisk.domain.entities.SavingGoal;

@Component
public class SavingGoalToUpdateSavingGoalResponseMapper extends BaseMapper<SavingGoal, UpdateSavingGoalResponse> {

    public SavingGoalToUpdateSavingGoalResponseMapper(ModelMapper mapper) {
        super(mapper);
    }

   @Override
   protected void doComplexMap(SavingGoal source, UpdateSavingGoalResponse target) {
        target.setStartDate(source.getCreatedAt().toString());
        target.setEndDate(source.getEndDate().toString());
   }

}
