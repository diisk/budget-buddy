package br.dev.diisk.domain.repositories.transaction;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.transaction.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.category c
            JOIN FETCH t.user u
            JOIN FETCH t.fundStorage fs
            WHERE t.id = :id
            AND u.id = :userId
            """)
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.category c
            JOIN FETCH t.user u
            JOIN FETCH t.fundStorage fs
            WHERE t.user.id = :id
            """)
    Set<Transaction> findAllByUserId(Long id);

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.category c
            JOIN FETCH t.user u
            JOIN FETCH t.fundStorage fs
            WHERE (
                t.user.id = :userId
                AND t.category.id = :categoryId
                AND (
                    (
                        :beginsAt is NULL
                        AND t.date <= :endsAt
                    )
                    OR t.date BETWEEN :beginsAt AND :endsAt
                )
            )
            """)
    Set<Transaction> findAllInPeriod(Long userId, Long categoryId, LocalDateTime beginsAt,
            LocalDateTime endsAt);

}
