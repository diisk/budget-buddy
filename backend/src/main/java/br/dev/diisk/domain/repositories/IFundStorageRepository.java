package br.dev.diisk.domain.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.FundStorage;

public interface IFundStorageRepository extends JpaRepository<FundStorage, Long> {

    @Query("""
            SELECT br FROM FundStorage br
            JOIN br.user u
            WHERE (
                u.id = :userId
                AND br.id = :id
            )
            """)
    FundStorage findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT br FROM FundStorage br
            JOIN br.user u
            WHERE u.id = :id
            """)
    Set<FundStorage> findAllByUserId(Long id);

    @Query("""
            SELECT count(*) > 0 FROM FundStorage br
            JOIN br.user u
            WHERE (
                u.id = :userId
                AND br.id = :id
            )
            """)
    Boolean isValidFundStorage(Long id, Long userId);
}
