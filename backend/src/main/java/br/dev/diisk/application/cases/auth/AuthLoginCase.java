package br.dev.diisk.application.cases.auth;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.exceptions.authentication.InvalidUserException;
import br.dev.diisk.application.interfaces.auth.IAuthLoginCase;
import br.dev.diisk.application.interfaces.auth.IAuthService;
import br.dev.diisk.application.interfaces.auth.ITokenService;
import br.dev.diisk.domain.entities.user.User;

@Service
public class AuthLoginCase implements IAuthLoginCase{

    private final IAuthService authService;
    private final ITokenService tokenService;
    
    public AuthLoginCase(IAuthService authService, ITokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @Override
    public String execute(LoginRequest dto) {
        User user;
        try{
            user = authService.authenticate(dto.getEmail(), dto.getPassword());
        }catch(Exception ex){
            System.err.println(ex);
            throw new InvalidUserException();
        }
        String token = tokenService.generateToken(user);
        return token;
    }

}
