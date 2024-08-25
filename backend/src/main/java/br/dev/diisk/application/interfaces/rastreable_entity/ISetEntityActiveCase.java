package br.dev.diisk.application.interfaces.rastreable_entity;

import br.dev.diisk.domain.entities.user.User;

public interface ISetEntityActiveCase {

    void execute(Long id, Boolean active, User user);

}
