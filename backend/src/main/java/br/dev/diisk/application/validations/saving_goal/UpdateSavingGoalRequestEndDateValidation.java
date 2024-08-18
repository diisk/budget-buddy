package br.dev.diisk.application.validations.saving_goal;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;


import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.application.exceptions.date.PastDateException;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class UpdateSavingGoalRequestEndDateValidation implements IUpdateSavingGoalValidator {

    @Override
    public void validate(Long id, UpdateSavingGoalRequest dto, User user) {
        if(dto.getEndDate().isBefore(LocalDateTime.now()))
            throw new PastDateException("endDate");
    }

}
