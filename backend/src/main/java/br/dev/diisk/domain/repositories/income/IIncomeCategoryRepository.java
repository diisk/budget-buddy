package br.dev.diisk.domain.repositories.income;

import java.util.Optional;
import java.util.Set;

import br.dev.diisk.domain.entities.income.IncomeCategory;

public interface IIncomeCategoryRepository {

    Set<IncomeCategory> findAllByUserId(Long id);

    Optional<IncomeCategory> findByIdAndUserId(Long id, Long userId);

    Boolean existsByNameAndUserId(String name, Long id);

    IncomeCategory save(IncomeCategory entity);

}
