package br.dev.diisk.application.interfaces.auth;

import br.dev.diisk.application.dtos.auth.LoginRequest;

public interface IAuthLoginCase {

    String execute(LoginRequest dto);

}
