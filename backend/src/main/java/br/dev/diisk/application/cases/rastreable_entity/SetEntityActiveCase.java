package br.dev.diisk.application.cases.rastreable_entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.rastreable_entity.ISetEntityActiveCase;
import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.interfaces.IEntityWithUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SetEntityActiveCase<E extends RastreableEntity & IEntityWithUser>
        implements ISetEntityActiveCase {

    private final JpaRepository<E, Long> repository;

    @Override
    @Transactional
    public void execute(Long id, Boolean active, User user) {
        Optional<E> entity = repository.findById(id);
        if (entity.isEmpty() || entity.get().getUser().getId() != user.getId())
            throw new DbValueNotFoundException(getClass(), "id");

        if (active) {
            entity.get().revive();
        } else {
            entity.get().remove();
        }
        repository.save(entity.get());
    }
}
