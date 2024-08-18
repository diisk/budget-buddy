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
import br.dev.diisk.application.interfaces.dashboard.IGetSummaryCase;
import br.dev.diisk.application.dtos.SummaryResponse;
import br.dev.diisk.application.dtos.response.SuccessResponse;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/dashboard")
@PreAuthorize("hasAuthority('DEFAULT')")
@SecurityRequirement(name = "bearer-key")
public class DashboardController {

    private final IResponseService responseService;
    private final IGetSummaryCase getSummaryCase;

    public DashboardController(IResponseService responseService, IGetSummaryCase getSummaryCase) {
        this.responseService = responseService;
        this.getSummaryCase = getSummaryCase;
    }

    @GetMapping("summary")
    public ResponseEntity<SuccessResponse<SummaryResponse>> getSummary(
            @RequestParam(required = false) LocalDateTime beginsAt,
            @RequestParam(required = false) LocalDateTime endsAt,
            @AuthenticationPrincipal User user) {

        SummaryResponse response = getSummaryCase.execute(beginsAt, endsAt, user);
        return responseService.ok(response);
    }

}
