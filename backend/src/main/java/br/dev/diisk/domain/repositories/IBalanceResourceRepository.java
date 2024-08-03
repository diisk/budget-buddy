package br.dev.diisk.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.BalanceResource;


public interface IBalanceResourceRepository extends JpaRepository<BalanceResource, Long> {

    @Query("""
            SELECT br FROM BalanceResource br
            JOIN br.user u
            WHERE (
                u.id = :id
                AND lower(br.name) = lower(:name)
            )
            """)
    BalanceResource findByNameAndUserId(String name, Long id);
}
