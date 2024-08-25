package br.dev.diisk.application.validations.saving_goal;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalRequest;
import br.dev.diisk.application.exceptions.date.PastDateException;
import br.dev.diisk.application.interfaces.saving_goal.IAddSavingGoalValidator;
import br.dev.diisk.domain.entities.user.User;

@Component
public class AddSavingGoalRequestEndDateValidation implements IAddSavingGoalValidator {

    @Override
    public void validate(AddSavingGoalRequest dto, User user) {
        if (dto.getEndDate().isBefore(LocalDateTime.now()))
            throw new PastDateException(getClass(), "endDate");
    }

}
