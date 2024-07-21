package br.dev.diisk.infra.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.dev.diisk.domain.entities.user.UserPerfil;
import br.dev.diisk.domain.repositories.user.IUserPerfilRepository;
import br.dev.diisk.infra.jpas.user.IUserPerfilJPA;

@Repository
public class UserPerfilRepository implements IUserPerfilRepository {

    @Autowired
    private IUserPerfilJPA jpa;

    @Override
    public UserPerfil findByName(String name) {
        return jpa.findByName(name);
    }

    @Override
    public UserPerfil save(UserPerfil perfil) {
        return jpa.save(perfil);
    }

}
