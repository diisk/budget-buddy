package br.dev.diisk.application.interfaces.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import br.dev.diisk.domain.entities.SavingGoal;

public interface IListActiveSavingGoalsCase {

    Set<SavingGoal> execute(Long userId, LocalDateTime referenceDate);

}
