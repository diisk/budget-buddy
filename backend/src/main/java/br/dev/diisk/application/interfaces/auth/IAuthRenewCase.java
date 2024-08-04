package br.dev.diisk.application.interfaces.auth;

import br.dev.diisk.domain.entities.user.User;

public interface IAuthRenewCase {

    String execute(User user);

}
