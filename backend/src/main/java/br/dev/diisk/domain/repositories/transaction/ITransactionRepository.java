package br.dev.diisk.domain.repositories.transaction;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.transaction.Transaction;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.category c
            WHERE t.user.id = :id
            """)
    Set<Transaction> findAllByUserId(Long id);

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.category c
            JOIN FETCH t.storage s
            WHERE (
                t.user.id = :userId
                AND t.type = :type
                AND (
                    (
                        :beginsAt is NULL
                        AND t.date <= :endsAt
                    )
                    OR t.date BETWEEN :beginsAt AND :endsAt
                )
            )
            """)
    Set<Transaction> findAllInPeriod(Long userId, TransactionTypeEnum type, LocalDateTime beginsAt,
            LocalDateTime endsAt);

}
