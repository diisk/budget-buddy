package br.dev.diisk.domain.repositories.user;

import br.dev.diisk.domain.entities.user.UserPerfil;

public interface IUserPerfilRepository {
    UserPerfil findByName(String name);

    UserPerfil save(UserPerfil perfil);
}
