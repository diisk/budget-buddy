package br.dev.diisk.application.validations.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.UtilService;
import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalRequest;
import br.dev.diisk.application.exceptions.persistence.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.saving_goal.IAddSavingGoalValidator;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddSavingGoalRequestExistsValidation implements IAddSavingGoalValidator {

    private final IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private final UtilService utilService;

    @Override
    public void validate(AddSavingGoalRequest dto, User user) {
        Set<SavingGoal> savings = listActiveSavingGoalsCase.execute(user.getId(), LocalDateTime.now());
        if (savings.stream()
                .anyMatch(sav -> utilService.equalsIgnoreCaseAndAccents(sav.getGoalName(), dto.getGoalName())))
            throw new ValueAlreadyInDatabaseException(getClass(), "goalName");
    }

}
