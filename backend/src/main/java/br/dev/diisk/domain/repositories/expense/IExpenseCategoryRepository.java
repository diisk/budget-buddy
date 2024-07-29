package br.dev.diisk.domain.repositories.expense;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.expense.ExpenseCategory;

public interface IExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Query("""
            SELECT ic FROM ExpenseCategory ic
            JOIN ic.user u
            WHERE u.id = :id
            """)
    Set<ExpenseCategory> findAllByUserId(Long id);

    @Query("""
            SELECT count(*) > 0 FROM ExpenseCategory ic
            JOIN ic.user u
            WHERE (
                u.id = :id
                AND lower(ic.name) = lower(:name)
            )
            """)
    Boolean existsByNameAndUserId(String name, Long id);

    @Query("""
            SELECT ic FROM ExpenseCategory ic
            JOIN ic.user u
            WHERE
            ic.id = :id
            AND u.id = :userId
            """)
    Optional<ExpenseCategory> findByIdAndUserId(Long id, Long userId);

}
