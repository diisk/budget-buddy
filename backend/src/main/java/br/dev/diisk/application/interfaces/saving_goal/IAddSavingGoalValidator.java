package br.dev.diisk.application.interfaces.saving_goal;

import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAddSavingGoalValidator {

    void validate(AddSavingGoalRequest dto, User user);

}
