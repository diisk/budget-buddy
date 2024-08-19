package br.dev.diisk.domain.repositories.monthly_history;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.diisk.domain.entities.MonthlyHistory;

public interface IMonthlyHistoriRepository extends JpaRepository<MonthlyHistory, Long> {

        @Query("""
                        SELECT mh FROM MonthlyHistory mh
                        JOIN mh.user u
                        WHERE (
                            u.id = :userId
                            AND mh.referenceDate = :referenceDate
                        )
                        """)
        Optional<MonthlyHistory> findByReferenceDateAndUserId(LocalDateTime referenceDate, Long userId);

        @Query("""
                        SELECT mh FROM MonthlyHistory mh
                        JOIN mh.user u
                        WHERE u.id = :userId
                        AND (
                            :beginsReference IS NULL
                            OR mh.referenceDate >= :beginsReference
                        )
                        AND mh.referenceDate <= :endsReference
                        """)
        Set<MonthlyHistory> findAllByUserIdAndPeriodReference(Long userId, LocalDateTime beginsReference,
                        LocalDateTime endsReference);

}
