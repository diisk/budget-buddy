package br.dev.diisk.infra.jpas.user;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.domain.entities.user.User;

public interface IUserJPA extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
