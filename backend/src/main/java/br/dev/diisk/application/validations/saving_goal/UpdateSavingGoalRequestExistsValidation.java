package br.dev.diisk.application.validations.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.UtilService;
import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.persistence.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalValidator;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateSavingGoalRequestExistsValidation implements IUpdateSavingGoalValidator {

    private final IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private final UtilService utilService;

    @Override
    public void validate(Long id, UpdateSavingGoalRequest dto, User user) {
        Set<SavingGoal> savings = listActiveSavingGoalsCase.execute(user.getId(), LocalDateTime.now());
        if (savings.stream()
                .noneMatch(sav -> sav.getId() == id))
            throw new DbValueNotFoundException(getClass(), "id");
        if (savings.stream()
                .anyMatch(sav -> sav.getId() != id
                        && utilService.equalsIgnoreCaseAndAccents(sav.getGoalName(), dto.getGoalName())))
            throw new ValueAlreadyInDatabaseException(getClass(), "goalName");
    }

}
