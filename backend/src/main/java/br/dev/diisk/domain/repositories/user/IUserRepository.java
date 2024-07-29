package br.dev.diisk.domain.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.domain.entities.user.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
