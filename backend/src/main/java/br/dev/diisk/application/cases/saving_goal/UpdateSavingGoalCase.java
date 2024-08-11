package br.dev.diisk.application.cases.saving_goal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.application.interfaces.saving_goal.IListActiveSavingGoalsCase;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalCase;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalValidator;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.saving_goal.ISavingGoalRepository;
import br.dev.diisk.infra.services.CacheService;
import jakarta.transaction.Transactional;

@Service
public class UpdateSavingGoalCase implements IUpdateSavingGoalCase {

    private IListActiveSavingGoalsCase listActiveSavingGoalsCase;
    private ISavingGoalRepository savingGoalRepository;
    private ModelMapper mapper;
    private List<IUpdateSavingGoalValidator> validations;
    private CacheService cacheService;
    
    public UpdateSavingGoalCase(IListActiveSavingGoalsCase listActiveSavingGoalsCase,
            ISavingGoalRepository savingGoalRepository, ModelMapper mapper,
            List<IUpdateSavingGoalValidator> validations, CacheService cacheService) {
        this.listActiveSavingGoalsCase = listActiveSavingGoalsCase;
        this.savingGoalRepository = savingGoalRepository;
        this.mapper = mapper;
        this.validations = validations;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional
    public SavingGoal execute(Long id, UpdateSavingGoalRequest dto, User user) {

        validations.forEach(validation->validation.validate(id, dto, user));

        cacheService.evictCache("saving-goals", user.getId().toString());

        Set<SavingGoal> savingGoals = listActiveSavingGoalsCase.execute(user.getId(), LocalDateTime.now());
        SavingGoal savingGoal = savingGoals.stream().filter(sg -> sg.getId() == id).findFirst().get();
        mapper.map(dto, savingGoal);
        return savingGoalRepository.save(savingGoal);
    }

}
