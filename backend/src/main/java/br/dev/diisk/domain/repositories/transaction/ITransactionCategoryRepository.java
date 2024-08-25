package br.dev.diisk.domain.repositories.transaction;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.transaction.TransactionCategory;
import br.dev.diisk.domain.enums.TransactionTypeEnum;

public interface ITransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

        @Query("""
                        SELECT tc FROM TransactionCategory tc
                        WHERE tc.user.id = :userId
                        AND tc.type = :type
                        """)
        Set<TransactionCategory> findAllByTypeAndUserId(Long userId, TransactionTypeEnum type);

        @Query("""
                        SELECT count(*) > 0 FROM TransactionCategory ic
                        JOIN ic.user u
                        WHERE (
                            u.id = :id
                            AND lower(ic.name) = lower(:name)
                        )
                        """)
        Boolean existsByNameAndUserId(String name, Long id);

}
