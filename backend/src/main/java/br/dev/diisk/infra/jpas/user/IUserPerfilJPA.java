package br.dev.diisk.infra.jpas.user;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.domain.entities.user.UserPerfil;



public interface IUserPerfilJPA extends JpaRepository<UserPerfil, Long> {

    UserPerfil findByName(String name);
}
