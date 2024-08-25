package br.dev.diisk.application.interfaces.rastreable_entity;

import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.interfaces.IEntityWithUser;

public interface IFindByIdAndUserIdCase<E extends RastreableEntity & IEntityWithUser> {

    E execute(Long id, User user);

}
