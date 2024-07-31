package br.dev.diisk.infra.controllers;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.dtos.GenericResponse;
import br.dev.diisk.application.dtos.DashboardSummaryResponse;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("dashboard")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class DashboardController {

    private IResponseService responseService;

    public DashboardController(IResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("summary")
    public ResponseEntity<GenericResponse<DashboardSummaryResponse>> getSummary(
            @RequestParam LocalDateTime beginsAt,
            @RequestParam LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {
        
        DashboardSummaryResponse response = new DashboardSummaryResponse();
        return responseService.ok(response);
    }

}
