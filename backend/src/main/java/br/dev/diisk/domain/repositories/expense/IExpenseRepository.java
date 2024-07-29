package br.dev.diisk.domain.repositories.expense;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.expense.Expense;

public interface IExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("""
            SELECT i FROM Expense i
            JOIN i.user u
            JOIN FETCH i.category c
            WHERE u.id = :id
            """)
    Set<Expense> findAllByUserId(Long id);

    @Query("""
            SELECT i FROM Expense i
            JOIN i.user u
            JOIN FETCH i.category c
            WHERE (
                u.id = :id
                AND i.date BETWEEN :beginsAt AND :endsAt
            )
            """)
    Set<Expense> findAllByUserIdAndPeriod(Long id, LocalDateTime beginsAt, LocalDateTime endsAt);

}
