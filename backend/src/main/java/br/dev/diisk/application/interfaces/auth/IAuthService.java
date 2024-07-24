package br.dev.diisk.application.interfaces.auth;

import br.dev.diisk.domain.dtos.auth.AuthLoginDTO;
import br.dev.diisk.domain.dtos.auth.AuthLoginResponseDTO;
import br.dev.diisk.domain.dtos.auth.AuthRegisterDTO;
import br.dev.diisk.domain.entities.user.User;

public interface IAuthService {

    public AuthLoginResponseDTO login(AuthLoginDTO dto);
    
    public AuthLoginResponseDTO renew(User user);

    public User register(AuthRegisterDTO dto);

}
