package br.dev.diisk.application.cases.auth;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.interfaces.auth.IAuthLoginCase;
import br.dev.diisk.application.interfaces.auth.IAuthService;
import br.dev.diisk.application.interfaces.auth.ITokenService;
import br.dev.diisk.domain.entities.user.User;

@Service
public class AuthLoginCase implements IAuthLoginCase{

    private IAuthService authService;
    private ITokenService tokenService;
    
    public AuthLoginCase(IAuthService authService, ITokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Override
    public String execute(LoginRequest dto) {
        User user = authService.authenticate(dto.getEmail(), dto.getPassword());
        String token = tokenService.generateToken(user);
        return token;
    }

}
