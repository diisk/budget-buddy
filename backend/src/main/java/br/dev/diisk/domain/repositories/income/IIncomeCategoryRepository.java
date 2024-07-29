package br.dev.diisk.domain.repositories.income;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.income.IncomeCategory;

public interface IIncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    @Query("""
            SELECT ic FROM IncomeCategory ic
            JOIN ic.user u
            WHERE u.id = :id
            """)
    Set<IncomeCategory> findAllByUserId(Long id);

    @Query("""
            SELECT count(*) > 0 FROM IncomeCategory ic
            JOIN ic.user u
            WHERE (
                u.id = :id
                AND lower(ic.name) = lower(:name)
            )
            """)
    Boolean existsByNameAndUserId(String name, Long id);

    @Query("""
            SELECT ic FROM IncomeCategory ic
            JOIN ic.user u
            WHERE
            ic.id = :id
            AND u.id = :userId
            """)
    Optional<IncomeCategory> findByIdAndUserId(Long id, Long userId);

}
