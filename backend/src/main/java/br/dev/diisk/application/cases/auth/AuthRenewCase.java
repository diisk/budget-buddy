package br.dev.diisk.application.cases.auth;

import org.springframework.stereotype.Service;

import br.dev.diisk.application.interfaces.auth.IAuthRenewCase;
import br.dev.diisk.application.interfaces.auth.ITokenService;
import br.dev.diisk.domain.entities.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthRenewCase implements IAuthRenewCase{

    private final ITokenService tokenService;

    @Override
    public String execute(User user) {
        String token = tokenService.generateToken(user);
        return token;
    }

}
