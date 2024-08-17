package br.dev.diisk.infra.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.saving_goal.IAddSavingGoalCase;
import br.dev.diisk.application.interfaces.saving_goal.IUpdateSavingGoalCase;
import br.dev.diisk.application.mappers.saving_goal.SavingGoalToAddSavingGoalResponseMapper;
import br.dev.diisk.application.mappers.saving_goal.SavingGoalToUpdateSavingGoalResponseMapper;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalRequest;
import br.dev.diisk.application.dtos.saving_goal.AddSavingGoalResponse;
import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalRequest;
import br.dev.diisk.application.dtos.saving_goal.UpdateSavingGoalResponse;
import br.dev.diisk.domain.entities.SavingGoal;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.infra.services.ResponseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/saving-goal")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class SavingGoalController {

    private final ResponseService responseService;
    private final IAddSavingGoalCase addSavingGoalCase;
    private final SavingGoalToAddSavingGoalResponseMapper savingGoalToAddSavingGoalResponseMapper;
    private final IUpdateSavingGoalCase updateSavingGoalCase;
    private final SavingGoalToUpdateSavingGoalResponseMapper savingGoalToUpdateSavingGoalResponseMapper;

    public SavingGoalController(ResponseService responseService, IAddSavingGoalCase addSavingGoalCase,
            SavingGoalToAddSavingGoalResponseMapper savingGoalToAddSavingGoalResponseMapper,
            IUpdateSavingGoalCase updateSavingGoalCase,
            SavingGoalToUpdateSavingGoalResponseMapper savingGoalToUpdateSavingGoalResponseMapper) {
        this.responseService = responseService;
        this.addSavingGoalCase = addSavingGoalCase;
        this.savingGoalToAddSavingGoalResponseMapper = savingGoalToAddSavingGoalResponseMapper;
        this.updateSavingGoalCase = updateSavingGoalCase;
        this.savingGoalToUpdateSavingGoalResponseMapper = savingGoalToUpdateSavingGoalResponseMapper;
    }

    @PostMapping
    public ResponseEntity<GenericResponse<AddSavingGoalResponse>> addSavingGoal(
            @Valid @RequestBody AddSavingGoalRequest dto,
            @AuthenticationPrincipal User user) {
        SavingGoal savingGoal = addSavingGoalCase.execute(dto, user);
        AddSavingGoalResponse response = savingGoalToAddSavingGoalResponseMapper.apply(savingGoal);
        return responseService.ok(response);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<GenericResponse<UpdateSavingGoalResponse>> updateSavingGoal(@PathVariable Long id,
            @Valid @RequestBody UpdateSavingGoalRequest dto,
            @AuthenticationPrincipal User user) {
        SavingGoal savingGoal = updateSavingGoalCase.execute(id, dto, user);
        UpdateSavingGoalResponse response = savingGoalToUpdateSavingGoalResponseMapper.apply(savingGoal);
        return responseService.ok(response);
    }

}
