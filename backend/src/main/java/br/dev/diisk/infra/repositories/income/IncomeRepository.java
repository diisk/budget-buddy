package br.dev.diisk.infra.repositories.income;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.dev.diisk.domain.entities.income.Income;
import br.dev.diisk.domain.repositories.income.IIncomeRepository;
import br.dev.diisk.infra.jpas.income.IIncomeJPA;

@Repository
public class IncomeRepository implements IIncomeRepository {

    @Autowired
    private IIncomeJPA jpa;

    @Override
    public Income save(Income income) {
        return jpa.save(income);
    }

    @Override
    public Set<Income> findAllByUserId(Long id) {
        return jpa.findAllByUserId(id);
    }

    @Override
    public Set<Income> findAllByUserIdAndPeriod(Long id, LocalDateTime beginsAt, LocalDateTime endsAt) {
        return jpa.findAllByUserIdAndPeriod(id, beginsAt, endsAt);
    }

    @Override
    public Optional<Income> findById(Long id) {
        return jpa.findById(id);
    }

}
