package br.dev.diisk.application.validations.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Component;

import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.application.exceptions.DbValueNotFoundException;
import br.dev.diisk.application.exceptions.ValueAlreadyInDatabaseException;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalValidator;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.infra.services.UtilService;

@Component
public class UpdateSavingGoalRequestExistsValidation implements IUpdateSavingGoalValidator {

    private final IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private final UtilService utilService;

    public UpdateSavingGoalRequestExistsValidation(IListActiveSavingGoalsCase listActiveSavingGoalsCase,
            UtilService utilService) {
        this.listActiveSavingGoalsCase = listActiveSavingGoalsCase;
        this.utilService = utilService;
    }

    @Override
    public void validate(Long id, UpdateSavingGoalRequest dto, User user) {
        Set<SavingGoal> savings = listActiveSavingGoalsCase.execute(user.getId(), LocalDateTime.now());
        if (savings.stream()
                .noneMatch(sav -> sav.getId() == id))
            throw new DbValueNotFoundException("id", SavingGoal.class.getSimpleName());
        if (savings.stream()
                .anyMatch(sav -> sav.getId() != id
                        && utilService.equalsIgnoreCaseAndAccents(sav.getGoalName(), dto.getGoalName())))
            throw new ValueAlreadyInDatabaseException("goalName", SavingGoal.class.getSimpleName());
    }

}
