package br.dev.diisk.application.cases.saving_goal;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.repositories.saving_goal.ISavingGoalRepository;

@Service
public class ListActiveSavingGoalsCase implements IListActiveSavingGoalsCase {

    private ISavingGoalRepository savingGoalRepository;

    public ListActiveSavingGoalsCase(ISavingGoalRepository savingGoalRepository) {
        this.savingGoalRepository = savingGoalRepository;
    }

    @Override
    public Set<SavingGoal> execute(Long userId, LocalDateTime referenceDate) {
        return savingGoalRepository.findActivesByUser(userId, referenceDate);
    }

}
