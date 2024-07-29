package br.dev.diisk.domain.repositories.income;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.income.Income;

public interface IIncomeRepository extends JpaRepository<Income, Long> {

    @Query("""
            SELECT i FROM Income i
            JOIN i.user u
            JOIN FETCH i.category c
            WHERE u.id = :id
            """)
    Set<Income> findAllByUserId(Long id);

    @Query("""
            SELECT i FROM Income i
            JOIN i.user u
            JOIN FETCH i.category c
            WHERE (
                u.id = :id
                AND i.date BETWEEN :beginsAt AND :endsAt
            )
            """)
    Set<Income> findAllByUserIdAndPeriod(Long id, LocalDateTime beginsAt, LocalDateTime endsAt);

}
