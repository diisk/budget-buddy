package br.dev.diisk.infra.repositories.income;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.dev.diisk.domain.entities.income.IncomeCategory;
import br.dev.diisk.domain.repositories.income.IIncomeCategoryRepository;
import br.dev.diisk.infra.jpas.income.IIncomeCategoryJPA;

@Repository
public class IncomeCategoryRepository implements IIncomeCategoryRepository {

    @Autowired
    private IIncomeCategoryJPA jpa;

    @Override
    public Set<IncomeCategory> findAllByUserId(Long userId) {
        return jpa.findAllByUserId(userId);
    }

    @Override
    public IncomeCategory save(IncomeCategory entity) {
        return jpa.save(entity);
    }

    @Override
    public Boolean existsByNameAndUserId(String name, Long userId) {
        return jpa.existsByNameAndUserId(name,userId);
    }

    @Override
    public Optional<IncomeCategory> findByIdAndUserId(Long id,Long userId) {
        return jpa.findByIdAndUserId(id,userId);
    }

}
