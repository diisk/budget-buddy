package br.dev.diisk.domain.repositories.user;

import br.dev.diisk.domain.entities.user.User;

public interface IUserRepository {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    User save(User user);
}
