package br.dev.diisk.application.cases.saving_goal;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalRequest;
import br.dev.diisk.application.interfaces.saving_goal.IAddSavingGoalCase;
import br.dev.diisk.application.interfaces.saving_goal.IAddSavingGoalValidator;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.saving_goal.ISavingGoalRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddSavingGoalCase implements IAddSavingGoalCase {

    private final ISavingGoalRepository savingGoalRepository;
    private final List<IAddSavingGoalValidator> validations;
    private final CacheService cacheService;

    @Override
    @Transactional
    public SavingGoal execute(AddSavingGoalRequest dto, User user) {
        
        validations.forEach(validation->validation.validate(dto, user));

        cacheService.evictCache("saving-goals", user.getId().toString());

        SavingGoal savingGoal = new SavingGoal();
        savingGoal.setCurrentValue(BigDecimal.ZERO);
        savingGoal.setEndDate(dto.getEndDate());
        savingGoal.setGoalName(dto.getGoalName());
        savingGoal.setTargetValue(dto.getTargetValue());
        savingGoal.setUser(user);
        return savingGoalRepository.save(savingGoal);
    }

}
