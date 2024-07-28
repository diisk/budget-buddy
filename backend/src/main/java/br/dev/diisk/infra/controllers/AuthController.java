package br.dev.diisk.infra.controllers;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.dev.diisk.application.interfaces.IResponseService;
import br.dev.diisk.application.interfaces.auth.IAuthService;
import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.infra.dtos.auth.LoginResponse;
import br.dev.diisk.domain.entities.GenericResponse;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.infra.dtos.auth.RegisterResponse;
import br.dev.diisk.infra.dtos.auth.RenewResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private IAuthService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IResponseService responseService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest dto) {
        String token = service.login(dto);
        return responseService.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest dto,
            UriComponentsBuilder uriBuilder) {
        User newUser = service.register(dto);
        RegisterResponse response = mapper.map(newUser, RegisterResponse.class);
        URI uri = uriBuilder.path("users/{id}").buildAndExpand(response.getId()).toUri();
        return responseService.created(uri, response);
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/renew")
    public ResponseEntity<GenericResponse<RenewResponse>> renew(@AuthenticationPrincipal User user) {
        String token = service.renew(user);
        return responseService.ok(new RenewResponse(token));
    }

}
