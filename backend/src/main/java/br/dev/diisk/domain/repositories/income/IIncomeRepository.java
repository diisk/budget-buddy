package br.dev.diisk.domain.repositories.income;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import br.dev.diisk.domain.entities.income.Income;

public interface IIncomeRepository {

    Optional<Income> findById(Long id);

    Income save(Income income);

    Set<Income> findAllByUserId(Long id);

    Set<Income> findAllByUserIdAndPeriod(Long id, LocalDateTime beginsAt, LocalDateTime endsAt);

}
