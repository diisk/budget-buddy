package br.dev.diisk.infra.controllers.auth;

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
import br.dev.diisk.domain.dtos.auth.AuthLoginDTO;
import br.dev.diisk.domain.dtos.auth.AuthLoginResponseDTO;
import br.dev.diisk.domain.dtos.auth.AuthRegisterDTO;
import br.dev.diisk.domain.dtos.auth.AuthRegisterResponseDTO;
import br.dev.diisk.domain.entities.GenericResponse;
import br.dev.diisk.domain.entities.user.User;
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
    public ResponseEntity<GenericResponse<AuthLoginResponseDTO>> login(@RequestBody @Valid AuthLoginDTO dto) {
        AuthLoginResponseDTO response = service.login(dto);
        return responseService.ok(response);
    }

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/register")
    public ResponseEntity<GenericResponse<AuthRegisterResponseDTO>> register(@RequestBody @Valid AuthRegisterDTO dto,
            @AuthenticationPrincipal User user,
            UriComponentsBuilder uriBuilder) {
        User newUser = service.register(dto, user);
        AuthRegisterResponseDTO response = mapper.map(newUser, AuthRegisterResponseDTO.class);
        URI uri = uriBuilder.path("users/{id}").buildAndExpand(response.getId()).toUri();
        return responseService.created(uri, response);
    }

    @PreAuthorize("hasAuthority('RENEW_TOKEN')")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/renew")
    public ResponseEntity<GenericResponse<AuthLoginResponseDTO>> renew(@AuthenticationPrincipal User user) {
        return responseService.ok(service.renew(user));
    }

}
