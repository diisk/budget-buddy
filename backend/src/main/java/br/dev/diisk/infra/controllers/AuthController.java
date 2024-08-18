package br.dev.diisk.infra.controllers;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.auth.IAuthLoginCase;
import br.dev.diisk.application.interfaces.auth.IAuthRegisterCase;
import br.dev.diisk.application.interfaces.auth.IAuthRenewCase;
import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.dtos.auth.LoginResponse;
import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.application.dtos.auth.RegisterResponse;
import br.dev.diisk.application.dtos.auth.RenewResponse;
import br.dev.diisk.application.dtos.response.SuccessResponse;
import br.dev.diisk.domain.entities.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final IAuthLoginCase authLoginCase;
    private final IAuthRegisterCase authRegisterCase;
    private final IAuthRenewCase authRenewCase;
    private final ModelMapper mapper;
    private final IResponseService responseService;

    public AuthController(IAuthLoginCase authLoginCase, IAuthRegisterCase authRegisterCase,
            IAuthRenewCase authRenewCase,
            ModelMapper mapper, IResponseService responseService) {
        this.authLoginCase = authLoginCase;
        this.authRegisterCase = authRegisterCase;
        this.authRenewCase = authRenewCase;
        this.mapper = mapper;
        this.responseService = responseService;
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest dto) {
        String token = authLoginCase.execute(dto);
        return responseService.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest dto,
            UriComponentsBuilder uriBuilder) {
        User newUser = authRegisterCase.execute(dto);
        RegisterResponse response = mapper.map(newUser, RegisterResponse.class);
        URI uri = uriBuilder.path("users/{id}").buildAndExpand(response.getId()).toUri();
        return responseService.created(uri, response);
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/renew")
    public ResponseEntity<SuccessResponse<RenewResponse>> renew(@AuthenticationPrincipal User user) {
        String token = authRenewCase.execute(user);
        return responseService.ok(new RenewResponse(token));
    }

}
