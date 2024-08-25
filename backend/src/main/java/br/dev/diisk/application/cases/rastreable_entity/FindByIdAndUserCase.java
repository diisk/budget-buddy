package br.dev.diisk.application.cases.rastreable_entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.diisk.application.exceptions.persistence.DbValueNotFoundException;
import br.dev.diisk.application.interfaces.rastreable_entity.IFindByIdAndUserIdCase;
import br.dev.diisk.domain.entities.RastreableEntity;
import br.dev.diisk.domain.entities.user.User;
import br.dev.diisk.domain.interfaces.IEntityWithUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class FindByIdAndUserCase<E extends RastreableEntity & IEntityWithUser>
        implements IFindByIdAndUserIdCase<E> {

    private final JpaRepository<E, Long> repository;

    @Override
    @Transactional
    public E execute(Long id, User user) {
        Optional<E> entity = repository.findById(id);

        if (entity.isEmpty() || entity.get().getUser().getId() != user.getId())
            throw new DbValueNotFoundException(getClass(), "id");

        return entity.get();
    }

}
