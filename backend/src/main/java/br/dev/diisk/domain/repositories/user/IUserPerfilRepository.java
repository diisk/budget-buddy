package br.dev.diisk.domain.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.domain.entities.user.UserPerfil;



public interface IUserPerfilRepository extends JpaRepository<UserPerfil, Long> {

    UserPerfil findByName(String name);
}
