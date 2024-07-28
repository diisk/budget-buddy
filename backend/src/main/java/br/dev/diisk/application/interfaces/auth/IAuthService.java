package br.dev.diisk.application.interfaces.auth;

import br.dev.diisk.application.dtos.auth.LoginRequest;
import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAuthService {

    public String login(LoginRequest dto);
    
    public String renew(User user);

    public User register(RegisterRequest dto);

}
