package br.dev.diisk.application.interfaces.auth;

import br.dev.diisk.application.dtos.auth.RegisterRequest;
import br.dev.diisk.domain.entities.user.User;

public interface IAuthRegisterCase {

    User execute(RegisterRequest dto);

}
