package br.dev.diisk.infra.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.repositories.user.IUserRepository;
import br.dev.diisk.infra.jpas.user.IUserJPA;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private IUserJPA jpa;

    @Override
    public User findByEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return jpa.save(user);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

}
