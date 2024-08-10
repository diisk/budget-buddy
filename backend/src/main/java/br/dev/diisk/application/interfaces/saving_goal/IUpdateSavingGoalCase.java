package br.dev.diisk.application.interfaces.saving_goal;

import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;

public interface IUpdateSavingGoalCase {

    SavingGoal execute(Long id, UpdateSavingGoalRequest dto, User user);

}
